package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN', 'ROLE_SHARE'])
@Transactional(readOnly = true)
class LabelController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Label.list(params), model:[labelCount: Label.count()]
    }

    def show(Label label) {
        respond label
    }

    def create() {
        respond new Label(params)
    }

    def displayImage(){
        def currentUser = springSecurityService?.currentUser?.username
        def count = 0
        if (currentUser?.toString()?.contains('.')){
            def forename = currentUser?.toString()?.split("\\.")[0]
            def surname = currentUser?.toString()?.split("\\.")[1]
            def expert = Expert.createCriteria().get {
                and{
                    eq("givenName", forename, [ignoreCase: true])
                    eq("familyName", surname, [ignoreCase: true])
                }
            }
            if(expert){
                count = Label.findAllByLabeler(expert)?.size()
            }
        }
        def patchList = Patch.findAllByLabelsIsEmpty().id
        def random = new Random()
        if(!patchList.empty){
            def patchInstanceId = patchList.get(random.nextInt(patchList.size()))
            def patchInstance = Patch.findById(patchInstanceId)
            def imagePath = patchInstance?.patchPath
            [imagePath:imagePath, patchId:patchInstance?.id, count: count]
        }
    }

    def reviewLabels(){
        def nextInstance = params.int('nextInstance')
        def previousInstance = params.int('previousInstance')
        def count = Label.list().size()
        def labelList = Label.list().sort {it?.id}
        def labelInstance
        if(nextInstance != null){
            def currentInstanceIndex = labelList.findIndexOf {it == labelList[nextInstance + 1]}
            labelInstance = labelList[currentInstanceIndex]
            def imagePath = labelInstance?.patch?.patchPath
            [imagePath:imagePath, labelName:labelInstance.labelName, count: count, currentInstance:currentInstanceIndex]
        }else if (previousInstance != null){
            def currentInstanceIndex = labelList.findIndexOf {it == labelList[previousInstance - 1]}
            labelInstance = labelList[currentInstanceIndex]
            def imagePath = labelInstance?.patch?.patchPath
            [imagePath:imagePath, labelName:labelInstance.labelName, count: count, currentInstance:currentInstanceIndex]
        }
        else {
            labelInstance = labelList[0]
            def imagePath = labelInstance?.patch?.patchPath
            [imagePath:imagePath, labelName:labelInstance.labelName, count: count, currentInstance:0]
        }
    }

    @Transactional
    def saveLabel(){
        def currentUser = springSecurityService?.currentUser?.username
        if (currentUser?.toString()?.contains('.')){
            def forename = currentUser?.toString()?.split("\\.")[0]
            def surname = currentUser?.toString()?.split("\\.")[1]
            def expert = Expert.createCriteria().get {
                and{
                    eq("givenName", forename, [ignoreCase: true])
                    eq("familyName", surname, [ignoreCase: true])
                }
            }
            def labelName = params.labelName
            def patch = Patch.findById(params.patchId)
            if(expert && patch && labelName){
                def label = new Label()
                label.patch = patch
                label.labeler = expert
                label.labelName = labelName
                label.save failOnError: true
                flash.message = "Previous label saved successfully."
                redirect action:"displayImage"
            }
            else {
                flash.message = "Previous label not saved."
                redirect action:"displayImage"
            }
        }else {
            redirect action:"displayImage"
        }
    }

    @Transactional
    def save(Label label) {
        if (label == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (label.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond label.errors, view:'create'
            return
        }

        label.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'label.label', default: 'Label'), label.id])
                redirect label
            }
            '*' { respond label, [status: CREATED] }
        }
    }

    def edit(Label label) {
        respond label
    }

    @Transactional
    def update(Label label) {
        if (label == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (label.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond label.errors, view:'edit'
            return
        }

        label.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'label.label', default: 'Label'), label.id])
                redirect label
            }
            '*'{ respond label, [status: OK] }
        }
    }

    @Transactional
    def delete(Label label) {

        if (label == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        label.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'label.label', default: 'Label'), label.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'label.label', default: 'Label'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

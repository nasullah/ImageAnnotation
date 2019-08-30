package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN', 'ROLE_SHARE'])
@Transactional(readOnly = true)
class LabelController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService
    def exportService

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
        def patchList = Patch.findAllByLabelsIsEmptyAndPathologyImageInList(PathologyImage.findAllByMultiplexImageInList(MultiplexImage.findAllByStudy(Study.findByStudyName('Prostate_Cancer_Annotations')))).id
        def random = new Random()
        if(!patchList.empty){
            def patchInstanceId = patchList.get(random.nextInt(patchList.size()))
            def patchInstance = Patch.findById(patchInstanceId)
            def imagePath = patchInstance?.patchPath
            [imagePath:imagePath, patchId:patchInstance?.id, count: count]
        }
    }

    def displayInkRemovalImagePatches(){
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
                count = Label.findAllByLabelerAndPatchInList(expert, Patch.findAllByPathologyImage(PathologyImage.findByImageIdentifier('Ink_Removal_Tiles')))?.size()
            }
        }
        def patchList = Patch.findAllByLabelsIsEmptyAndPathologyImageInList(PathologyImage.findAllByMultiplexImageInList(MultiplexImage.findAllByStudy(Study.findByStudyName('Ink_Removal_Review_Study')))).id
        def random = new Random()
        if(!patchList.empty){
            def patchInstanceId = patchList.get(random.nextInt(patchList.size()))
            def patchInstance = Patch.findById(patchInstanceId)
            def imagePath = patchInstance?.patchPath
            [imagePath:imagePath, patchId:patchInstance?.id, count: count]
        }
    }

    def displayTransplantImagePatches(){
        def currentUser = springSecurityService?.currentUser?.username
        def labelList = []
        def labeledPatchesList = []
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
                labelList = Label.findAllByLabeler(expert)
                count = labelList?.size()
                labeledPatchesList = labelList?.patch?.id

            }
        }
        def patchList = Patch.findAllByPathologyImageInList(PathologyImage.findAllByMultiplexImageInList(MultiplexImage.findAllByStudy(Study.findByStudyName('Kidney_Image_Annotation')))).id
        def newList = patchList - labeledPatchesList
        def random = new Random()
        if(!newList.empty){
            def patchInstanceId = newList.get(random.nextInt(newList.size()))
            def patchInstance = Patch.findById(patchInstanceId)
            def imagePath = patchInstance?.patchPath
            [imagePath:imagePath, patchId:patchInstance?.id, count: count]
        }
    }

    def reviewLabels(){
        def nextInstance = params.int('nextInstance')
        def previousInstance = params.int('previousInstance')
        def patchList = Patch.findAllByPathologyImageInList(PathologyImage.findAllByMultiplexImageInList(MultiplexImage.findAllByStudy(Study.findByStudyName('Prostate_Cancer_Annotations'))))
        def count = Label.findAllByPatchInList(patchList).size()
        def labelList = Label.findAllByPatchInList(patchList).sort {it?.id}
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

    def reviewTransplantLabels(){
        def nextInstance = params.int('nextInstance')
        def previousInstance = params.int('previousInstance')
        def patchList = Patch.findAllByPathologyImageInList(PathologyImage.findAllByMultiplexImageInList(MultiplexImage.findAllByStudy(Study.findByStudyName('Kidney_Image_Annotation'))))
        def count = Label.findAllByPatchInList(patchList).size()
        def labelList = Label.findAllByPatchInList(patchList).sort {it?.id}
        def labelInstance
        if(nextInstance != null){
            def currentInstanceIndex = labelList.findIndexOf {it == labelList[nextInstance + 1]}
            labelInstance = labelList[currentInstanceIndex]
            def imagePath = labelInstance?.patch?.patchPath
            def labelInformative = labelInstance?.labelName?.toString()?.split('_')[0]
            def labelHealthy = labelInstance?.labelName?.toString()?.split('_')[1]
            [imagePath:imagePath, labelInformative:labelInformative, labelHealthy:labelHealthy, count: count, currentInstance:currentInstanceIndex]
        }else if (previousInstance != null){
            def currentInstanceIndex = labelList.findIndexOf {it == labelList[previousInstance - 1]}
            labelInstance = labelList[currentInstanceIndex]
            def imagePath = labelInstance?.patch?.patchPath
            def labelInformative = labelInstance?.labelName?.toString()?.split('_')[0]
            def labelHealthy = labelInstance?.labelName?.toString()?.split('_')[1]
            [imagePath:imagePath, labelInformative:labelInformative, labelHealthy:labelHealthy, count: count, currentInstance:currentInstanceIndex]
        }
        else {
            labelInstance = labelList[0]
            def imagePath = labelInstance?.patch?.patchPath
            def labelInformative = labelInstance?.labelName?.toString()?.split('_')[0]
            def labelHealthy = labelInstance?.labelName?.toString()?.split('_')[1]
            [imagePath:imagePath, labelInformative:labelInformative, labelHealthy:labelHealthy, count: count, currentInstance:0]
        }
    }

    @Secured(['ROLE_ADMIN'])
    def exportLabels(){
        if(params?.extension && params?.extension != "html"){
            response.contentType = grailsApplication.config.grails.mime.types[params?.extension]
            response.setHeader("Content-disposition", "attachment; filename= Exported Labels.${params.extension}")
            def patchList = Patch.findAllByIterationNumber(params?.iterationNumber)
            def labelList = Label.findAllByPatchInList(patchList)
            List fields = ["patch.patchName", "patch.iterationNumber", "patch.confidence", "patch.pathologyImage.imageIdentifier", "patch.imageLevel", "patch.imageSize", "patch.xCoordinate",
                           "patch.yCoordinate", "labelName", "labeler.familyName"]
            Map labels = ["patch.confidence":"Confidence", "patch.pathologyImage.imageIdentifier":"Image file", "patch.imageLevel":"Level",
                          "patch.imageSize":"Image size", "patch.xCoordinate":"X coordinate","patch.yCoordinate":"Y coordinate",
                          "labelName":"Label", "labeler.familyName":"Annotator", "patch.iterationNumber":"Iteration Number"]
            def labeler = { domain, value ->
                return domain?.labeler?.givenName + " " + domain?.labeler?.familyName
            }
            Map formatters = ["labeler.familyName":labeler]
            Map parameters = [title: "Label"]
            exportService.export(params.extension, response.outputStream, labelList, fields, labels, formatters, parameters )
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
            def comment = params.comment
            def patch = Patch.findById(params.patchId)
            if(expert && patch && labelName){
                def label = new Label()
                label.patch = patch
                label.labeler = expert
                label.labelName = labelName
                label.comment = comment
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
    def saveRatings(){
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
            def labelInformative = params.labelInformative
            def labelHealthy = params.labelHealthy
            def labelName = labelInformative.toString() + '_' + labelHealthy.toString()
            def comment = params.comment
            def patch = Patch.findById(params.patchId)
            if(expert && patch && labelName){
                def label = new Label()
                label.patch = patch
                label.labeler = expert
                label.labelName = labelName
                label.comment = comment
                label.save failOnError: true
                flash.message = "Previous ratings saved successfully."
                redirect action:"displayTransplantImagePatches"
            }
            else {
                flash.message = "Previous ratings not saved."
                redirect action:"displayTransplantImagePatches"
            }
        }else {
            redirect action:"displayTransplantImagePatches"
        }
    }

    @Transactional
    def saveInkRemovalRatings(){
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
            def contentLoss = params.contentLoss
            def contentAdd = params.contentAdd
            def generalQuality = params.generalQuality
            def diagnosisQuality = params.diagnosisQuality
            def labelName = contentLoss.toString() + '_' + contentAdd.toString() + '_' + generalQuality.toString() + '_' + diagnosisQuality.toString()
            def comment = params.comment
            def patch = Patch.findById(params.patchId)
            if(expert && patch && labelName){
                def label = new Label()
                label.patch = patch
                label.labeler = expert
                label.labelName = labelName
                label.comment = comment
                label.save failOnError: true
                flash.message = "Previous ratings saved successfully."
                redirect action:"displayInkRemovalImagePatches"
            }
            else {
                flash.message = "Previous ratings not saved."
                redirect action:"displayTransplantImagePatches"
            }
        }else {
            redirect action:"displayInkRemovalImagePatches"
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

package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional(readOnly = true)
class FocusStatusController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FocusStatus.list(params), model:[focusStatusCount: FocusStatus.count()]
    }

    def show(FocusStatus focusStatus) {
        respond focusStatus
    }

    def create() {
        respond new FocusStatus(params)
    }

    def addFociStatus(){
        def multiplexImage = MultiplexImage.findById(params.imageId)
        def expert = Expert.findById(params.annotatorId)
        def foci = multiplexImage?.foci
        foci = foci.findAll {it.expert.id == expert.id }
        [foci: foci, multiplexImage:multiplexImage]
    }

    @Transactional
    def saveFocus(){
        def multiplexImage = MultiplexImage.findById(params.imageId)
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
            if(expert){
                List<FocusStatus> focusStatusList = new ArrayList<FocusStatus>();
                if (params.focus1){
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 1, expert)
                    if (focus){
                        focus.status = FocusStatusDesc.findById(params.focus1)
                        focusStatusList.add(focus)
                    }else {
                        focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:1, status:params.focus1, diagnosis:params.diagnosis1, diagnosisNameOther:params.diagnosisOther1))
                    }
                }else {
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 1, expert)
                    if (focus){
                        focus.delete flush:true
                    }
                }
                if (params.focus2){
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 2, expert)
                    if (focus){
                        focus.status = FocusStatusDesc.findById(params.focus2)
                        focusStatusList.add(focus)
                    }else {
                        focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:2, status:params.focus2, diagnosis:params.diagnosis2, diagnosisNameOther:params.diagnosisOther2))
                    }
                }else {
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 2, expert)
                    if (focus){
                        focus.delete flush:true
                    }
                }
                if (params.focus3){
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 3, expert)
                    if (focus){
                        focus.status = FocusStatusDesc.findById(params.focus3)
                        focusStatusList.add(focus)
                    }else {
                        focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:3, status:params.focus3, diagnosis:params.diagnosis3, diagnosisNameOther:params.diagnosisOther3))
                    }
                }else {
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 3, expert)
                    if (focus){
                        focus.delete flush:true
                    }
                }
                if (params.focus4){
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 4, expert)
                    if (focus){
                        focus.status = FocusStatusDesc.findById(params.focus4)
                        focusStatusList.add(focus)
                    }else {
                        focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:4, status:params.focus4, diagnosis:params.diagnosis4, diagnosisNameOther:params.diagnosisOther4))
                    }
                }else {
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 4, expert)
                    if (focus){
                        focus.delete flush:true
                    }
                }
                if (params.focus5){
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 5, expert)
                    if (focus){
                        focus.status = FocusStatusDesc.findById(params.focus5)
                        focusStatusList.add(focus)
                    }else {
                        focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:5, status:params.focus5, diagnosis:params.diagnosis5, diagnosisNameOther:params.diagnosisOther5))
                    }
                }else {
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 5, expert)
                    if (focus){
                        focus.delete flush:true
                    }
                }
                if (params.focus6){
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 6, expert)
                    if (focus){
                        focus.status = FocusStatusDesc.findById(params.focus6)
                        focusStatusList.add(focus)
                    }else {
                        focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:6, status:params.focus6, diagnosis:params.diagnosis6, diagnosisNameOther:params.diagnosisOther6))
                    }
                }else {
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 6, expert)
                    if (focus){
                        focus.delete flush:true
                    }
                }
                if (params.focus7){
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 7, expert)
                    if (focus){
                        focus.status = FocusStatusDesc.findById(params.focus7)
                        focusStatusList.add(focus)
                    }else {
                        focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:7, status:params.focus7, diagnosis:params.diagnosis7, diagnosisNameOther:params.diagnosisOther7))
                    }
                }else {
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 7, expert)
                    if (focus){
                        focus.delete flush:true
                    }
                }
                if (params.focus8){
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 8, expert)
                    if (focus){
                        focus.status = FocusStatusDesc.findById(params.focus8)
                        focusStatusList.add(focus)
                    }else {
                        focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:8, status:params.focus8, diagnosis:params.diagnosis8, diagnosisNameOther:params.diagnosisOther8))
                    }
                }else {
                    def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 8, expert)
                    if (focus){
                        focus.delete flush:true
                    }
                }
                multiplexImage.comment = params.comment
                multiplexImage.save failOnError: true
                for (int i = 0; i <focusStatusList.size(); i++ ){
                    multiplexImage.addToFoci(focusStatusList.get(i)).save failOnError: true
                }
                redirect(controller: "annotation", action: "showViewImageOnOS", params: [imageId: multiplexImage?.id, annotatorId: expert?.id])
            }
        }
    }


    @Transactional
    def save(FocusStatus focusStatus) {
        if (focusStatus == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (focusStatus.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond focusStatus.errors, view:'create'
            return
        }

        focusStatus.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'focusStatus.label', default: 'FocusStatus'), focusStatus.id])
                redirect focusStatus
            }
            '*' { respond focusStatus, [status: CREATED] }
        }
    }

    def edit(FocusStatus focusStatus) {
        respond focusStatus
    }

    @Transactional
    def update(FocusStatus focusStatus) {
        if (focusStatus == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (focusStatus.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond focusStatus.errors, view:'edit'
            return
        }

        focusStatus.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'focusStatus.label', default: 'FocusStatus'), focusStatus.id])
                redirect focusStatus
            }
            '*'{ respond focusStatus, [status: OK] }
        }
    }

    @Transactional
    def delete(FocusStatus focusStatus) {

        if (focusStatus == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        focusStatus.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'focusStatus.label', default: 'FocusStatus'), focusStatus.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'focusStatus.label', default: 'FocusStatus'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

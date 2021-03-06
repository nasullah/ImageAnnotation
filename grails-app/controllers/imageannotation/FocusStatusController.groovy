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
                def multiplexImageType = MultiplexImageType.findById(params.long('caseType'))
                if(multiplexImageType.multiplexImageTypeName == 'Control'){
                    List<FocusStatus> focusStatusList = new ArrayList<FocusStatus>();
                    if (params.focusType1){
                        def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 1, expert)
                        if (focus){
                            focus.focusType = FocusType.findById(params.long('focusType1'))
                            focusStatusList.add(focus)
                        }else {
                            focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:1, focusType: params.focusType1))
                        }
                    }else {
                        def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 1, expert)
                        if (focus){
                            focus.delete flush:true
                        }
                    }
                    if (params.focusType2){
                        def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 2, expert)
                        if (focus){
                            focus.focusType = FocusType.findById(params.long('focusType2'))
                            focusStatusList.add(focus)
                        }else {
                            focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:2, focusType: params.focusType2))
                        }
                    }else {
                        def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 2, expert)
                        if (focus){
                            focus.delete flush:true
                        }
                    }
                    if (params.focusType3){
                        def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 3, expert)
                        if (focus){
                            focus.focusType = FocusType.findById(params.long('focusType3'))
                            focusStatusList.add(focus)
                        }else {
                            focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:3, focusType: params.focusType3))
                        }
                    }else {
                        def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 3, expert)
                        if (focus){
                            focus.delete flush:true
                        }
                    }
                    if (params.focusType4){
                        def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 4, expert)
                        if (focus){
                            focus.focusType = FocusType.findById(params.long('focusType4'))
                            focusStatusList.add(focus)
                        }else {
                            focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:4, focusType: params.focusType4))
                        }
                    }else {
                        def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 4, expert)
                        if (focus){
                            focus.delete flush:true
                        }
                    }
                    multiplexImage.multiplexImageType = multiplexImageType
                    multiplexImage.save failOnError: true
                    for (int i = 0; i <focusStatusList.size(); i++ ){
                        multiplexImage.addToFoci(focusStatusList.get(i)).save failOnError: true
                    }
                }else {
                    List<FocusStatus> focusStatusList = new ArrayList<FocusStatus>();
                    if (params.focus1){
                        def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 1, expert)
                        if (focus){
                            focus.status = FocusStatusDesc.findById(params.long('focus1'))
                            focus.diagnosis = Diagnosis.findById(params.long('diagnosis1'))
                            focus.diagnosisNameOther = params.diagnosisOther1
                            focus.stainType = StainType.findById(params.long('stainingType1'))
                            focus.stainTypeNameOther = params.stainingTypeOther1
                            focusStatusList.add(focus)
                        }else {
                            focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:1, status:params.focus1, diagnosis:params.diagnosis1, diagnosisNameOther:params.diagnosisOther1, stainType: params.stainingType1, stainTypeNameOther: params.stainingTypeOther1))
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
                            focus.status = FocusStatusDesc.findById(params.long('focus2'))
                            focus.diagnosis = Diagnosis.findById(params.long('diagnosis2'))
                            focus.diagnosisNameOther = params.diagnosisOther2
                            focus.stainType = StainType.findById(params.long('stainingType2'))
                            focus.stainTypeNameOther = params.stainingTypeOther2
                            focusStatusList.add(focus)
                        }else {
                            focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:2, status:params.focus2, diagnosis:params.diagnosis2, diagnosisNameOther:params.diagnosisOther2, stainType: params.stainingType2, stainTypeNameOther: params.stainingTypeOther2))
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
                            focus.status = FocusStatusDesc.findById(params.long('focus3'))
                            focus.diagnosis = Diagnosis.findById(params.long('diagnosis3'))
                            focus.diagnosisNameOther = params.diagnosisOther3
                            focus.stainType = StainType.findById(params.long('stainingType3'))
                            focus.stainTypeNameOther = params.stainingTypeOther3
                            focusStatusList.add(focus)
                        }else {
                            focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:3, status:params.focus3, diagnosis:params.diagnosis3, diagnosisNameOther:params.diagnosisOther3, stainType: params.stainingType3, stainTypeNameOther: params.stainingTypeOther3))
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
                            focus.status = FocusStatusDesc.findById(params.long('focus4'))
                            focus.diagnosis = Diagnosis.findById(params.long('diagnosis4'))
                            focus.diagnosisNameOther = params.diagnosisOther4
                            focus.stainType = StainType.findById(params.long('stainingType4'))
                            focus.stainTypeNameOther = params.stainingTypeOther4
                            focusStatusList.add(focus)
                        }else {
                            focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:4, status:params.focus4, diagnosis:params.diagnosis4, diagnosisNameOther:params.diagnosisOther4, stainType: params.stainingType4, stainTypeNameOther: params.stainingTypeOther4))
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
                            focus.status = FocusStatusDesc.findById(params.long('focus5'))
                            focus.diagnosis = Diagnosis.findById(params.long('diagnosis5'))
                            focus.diagnosisNameOther = params.diagnosisOther5
                            focus.stainType = StainType.findById(params.long('stainingType5'))
                            focus.stainTypeNameOther = params.stainingTypeOther5
                            focusStatusList.add(focus)
                        }else {
                            focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:5, status:params.focus5, diagnosis:params.diagnosis5, diagnosisNameOther:params.diagnosisOther5, stainType: params.stainingType5, stainTypeNameOther: params.stainingTypeOther5))
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
                            focus.status = FocusStatusDesc.findById(params.long('focus6'))
                            focus.diagnosis = Diagnosis.findById(params.long('diagnosis6'))
                            focus.diagnosisNameOther = params.diagnosisOther6
                            focus.stainType = StainType.findById(params.long('stainingType6'))
                            focus.stainTypeNameOther = params.stainingTypeOther6
                            focusStatusList.add(focus)
                        }else {
                            focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:6, status:params.focus6, diagnosis:params.diagnosis6, diagnosisNameOther:params.diagnosisOther6, stainType: params.stainingType6, stainTypeNameOther: params.stainingTypeOther6))
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
                            focus.status = FocusStatusDesc.findById(params.long('focus7'))
                            focus.diagnosis = Diagnosis.findById(params.long('diagnosis7'))
                            focus.diagnosisNameOther = params.diagnosisOther7
                            focus.stainType = StainType.findById(params.long('stainingType7'))
                            focus.stainTypeNameOther = params.stainingTypeOther7
                            focusStatusList.add(focus)
                        }else {
                            focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:7, status:params.focus7, diagnosis:params.diagnosis7, diagnosisNameOther:params.diagnosisOther7, stainType: params.stainingType7, stainTypeNameOther: params.stainingTypeOther7))
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
                            focus.status = FocusStatusDesc.findById(params.long('focus8'))
                            focus.diagnosis = Diagnosis.findById(params.long('diagnosis8'))
                            focus.diagnosisNameOther = params.diagnosisOther8
                            focus.stainType = StainType.findById(params.long('stainingType8'))
                            focus.stainTypeNameOther = params.stainingTypeOther8
                            focusStatusList.add(focus)
                        }else {
                            focusStatusList.add(new FocusStatus(expert:expert.id, focusNumber:8, status:params.focus8, diagnosis:params.diagnosis8, diagnosisNameOther:params.diagnosisOther8, stainType: params.stainingType8, stainTypeNameOther: params.stainingTypeOther8))
                        }
                    }else {
                        def focus = FocusStatus.findByMultiplexImageAndFocusNumberAndExpert(multiplexImage, 8, expert)
                        if (focus){
                            focus.delete flush:true
                        }
                    }
                    multiplexImage.comment = params.comment
                    multiplexImage.multiplexImageType = multiplexImageType
                    multiplexImage.save failOnError: true
                    for (int i = 0; i <focusStatusList.size(); i++ ){
                        multiplexImage.addToFoci(focusStatusList.get(i)).save failOnError: true
                    }
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

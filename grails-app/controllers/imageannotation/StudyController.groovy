package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.converters.JSON

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional(readOnly = true)
class StudyController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService
    def exportService

    @Secured('ROLE_ADMIN')
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Study.list(params), model:[studyCount: Study.count()]
    }

    def show(Study study) {
        respond study
    }

    def create() {
        respond new Study(params)
    }

    def testapi(){
        def  studyList = Study.list()
        render studyList as JSON
    }

    def exportAudit() {
        if(params?.extension && params?.extension != "html"){
            response.contentType = grailsApplication.config.grails.mime.types[params?.extension]
            response.setHeader("Content-disposition", "attachment; filename= ${params.study}.${params.extension}")
            def study = Study.findByStudyName(params.study)
            def listMultiplexImage = MultiplexImage.findAllByStudy(study)
            def listAnnotation = Annotation.findAllByMultiplexImageInList(listMultiplexImage)
            def listAuditLogData = AuditTrail.findAllByPersistedObjectIdInListAndClassName(listAnnotation.id, 'Annotation')
            List fields = ["actor", "className", "dateCreated", "eventName", "id", "lastUpdated", "propertyName", "newValue",
                           "oldValue"]
            exportService.export(params.extension, response.outputStream, listAuditLogData, fields, [:], [:], [:] )
        }
    }

    def yourStudyList(){
        def currentUser = springSecurityService.currentUser.username
        if (currentUser){
            def forename = currentUser?.toString()?.split("\\.")[0]
            def surname = currentUser?.toString()?.split("\\.")[1]
            def expert = Expert.createCriteria().get {
                and{
                    eq("givenName", forename, [ignoreCase: true])
                    eq("familyName", surname, [ignoreCase: true])
                }
            }
            if(expert){
                def  studyList =Study.findAllByStudyOwner(expert)
                respond studyList
            }
        }
    }

    @Transactional
    def save(Study study) {
        if (study == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (study.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond study.errors, view:'create'
            return
        }

        study.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'study.label', default: 'Study'), study.id])
                redirect study
            }
            '*' { respond study, [status: CREATED] }
        }
    }

    def edit(Study study) {
        respond study
    }

    @Transactional
    def update(Study study) {
        if (study == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (study.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond study.errors, view:'edit'
            return
        }

        study.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'study.label', default: 'Study'), study.id])
                redirect study
            }
            '*'{ respond study, [status: OK] }
        }
    }

    @Secured('ROLE_ADMIN')
    @Transactional
    def delete(Study study) {

        if (study == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        study.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'study.label', default: 'Study'), study.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

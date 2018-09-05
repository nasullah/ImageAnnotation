package imageannotation

import grails.converters.*

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional(readOnly = true)
class AnnotationController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Annotation.list(params), model:[annotationCount: Annotation.count()]
    }

    def show(Annotation annotation) {
        respond annotation
    }

    def create() {
        respond new Annotation(params)
    }

    def viewImageOnOS(){
    }

    def backToList(){
        def imageId = params.imageId
        if (imageId){
            def study = MultiplexImage.findById(imageId).study
            redirect(controller: "multiplexImage", action: "yourImageList", params:[study: study?.studyName])
        }
    }

    def addComment(){
    }

    def getAnnotation() {
        def expert = Expert.findById(params.annotatorId)
        def multiplexImage = MultiplexImage.findById(params.imageId)
        if(expert && multiplexImage){
            def annotation
            if(multiplexImage?.study?.studyType?.studyTypeName == 'Shared'){
                annotation = Annotation.findAllByMultiplexImage(multiplexImage)
            }else{
                annotation = Annotation.findAllByImageAnnotatorAndMultiplexImageAndStageIsNull(expert, multiplexImage)
            }
            if (!annotation.empty){
                annotation = annotation.sort{it.id}
                render contentType: "text/json", text: annotation?.last()?.annotationData
            }
        }
    }

    @Transactional
    def saveAnnotationWithComment() {
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
                def annotation = new Annotation()
                annotation.multiplexImage = multiplexImage
                annotation.annotationData = 'No_annotations'
                annotation.imageAnnotator = expert
                annotation.comment = params.comment
                annotation.stage = params.stage
                annotation.save failOnError: true
                redirect(controller: "annotation", action: "viewImageOnOS", params: [imageId: annotation?.multiplexImage?.id, annotatorId: expert?.id])
            }
        }
    }

    @Transactional
    def saveAnnotation() {
        def multiplexImage = MultiplexImage.findById(request.JSON.imageId)
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
                def annotation = new Annotation()
                annotation.multiplexImage = multiplexImage
                annotation.annotationData = request.JSON
                annotation.imageAnnotator = expert
                annotation.save failOnError: true
                redirect(controller: "annotation", action: "viewImageOnOS", params: [imageId: annotation?.multiplexImage?.id])
            }
        }
    }

    @Transactional
    def save(Annotation annotation) {
        if (annotation == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (annotation.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond annotation.errors, view:'create'
            return
        }

        annotation.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'annotation.label', default: 'annotation'), annotation.id])
                redirect annotation
            }
            '*' { respond annotation, [status: CREATED] }
        }
    }

    def edit(Annotation annotation) {
        respond annotation
    }

    @Transactional
    def update(Annotation annotation) {
        if (annotation == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (annotation.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond annotation.errors, view:'edit'
            return
        }

        annotation.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'annotation.label', default: 'Annotation'), annotation.id])
                redirect annotation
            }
            '*'{ respond annotation, [status: OK] }
        }
    }

    @Transactional
    def delete(Annotation annotation) {

        if (annotation == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        annotation.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'annotation.label', default: 'Annotation'), annotation.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'annotation.label', default: 'Annotation'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

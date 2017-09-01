package imageannotation

import grails.converters.*

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional(readOnly = true)
class AnnotationController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

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

    def testAnnotationTools(){

    }

    def getAnnotation() {
        def annotation = Annotation.list()
        if (!annotation.empty){
            render contentType: "text/json", text: annotation?.last()?.annotationData
        }else {
            render contentType: "text/json", text: '[]'
        }
    }

    def testOpenSeaDragon(){

    }

    @Transactional
    def save() {
        def annotation = new Annotation(params)
        annotation.annotationData = request.JSON.annotationData
        annotation.save flush:true
        redirect(controller: "annotation", action: "testAnnotationTools")
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

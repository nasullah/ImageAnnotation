package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

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
                flash.message = message(code: 'default.created.message', args: [message(code: 'annotation.label', default: 'Annotation'), annotation.id])
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

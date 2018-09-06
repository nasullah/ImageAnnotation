package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional(readOnly = true)
class AssignmentController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Assignment.list(params), model:[assignmentCount: Assignment.count()]
    }

    def show(Assignment assignment) {
        respond assignment
    }

    def create() {
        respond new Assignment(params)
    }

    @Transactional
    def save(Assignment assignment) {
        if (assignment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (assignment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond assignment.errors, view:'create'
            return
        }

        assignment.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'assignment.label', default: 'Assignment'), assignment.id])
                redirect assignment
            }
            '*' { respond assignment, [status: CREATED] }
        }
    }

    def edit(Assignment assignment) {
        respond assignment
    }

    @Transactional
    def update(Assignment assignment) {
        if (assignment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (assignment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond assignment.errors, view:'edit'
            return
        }

        assignment.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'assignment.label', default: 'Assignment'), assignment.id])
                redirect assignment
            }
            '*'{ respond assignment, [status: OK] }
        }
    }

    @Transactional
    def delete(Assignment assignment) {

        if (assignment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        assignment.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'assignment.label', default: 'Assignment'), assignment.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'assignment.label', default: 'Assignment'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

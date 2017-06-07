package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class ExpertController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Expert.list(params), model:[expertCount: Expert.count()]
    }

    def show(Expert expert) {
        respond expert
    }

    def create() {
        respond new Expert(params)
    }

    @Transactional
    def save(Expert expert) {
        if (expert == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (expert.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond expert.errors, view:'create'
            return
        }

        expert.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'expert.label', default: 'Expert'), expert.id])
                redirect expert
            }
            '*' { respond expert, [status: CREATED] }
        }
    }

    def edit(Expert expert) {
        respond expert
    }

    @Transactional
    def update(Expert expert) {
        if (expert == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (expert.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond expert.errors, view:'edit'
            return
        }

        expert.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'expert.label', default: 'Expert'), expert.id])
                redirect expert
            }
            '*'{ respond expert, [status: OK] }
        }
    }

    @Transactional
    def delete(Expert expert) {

        if (expert == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        expert.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'expert.label', default: 'Expert'), expert.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'expert.label', default: 'Expert'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CentreController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Centre.list(params), model:[centreCount: Centre.count()]
    }

    def show(Centre centre) {
        respond centre
    }

    def create() {
        respond new Centre(params)
    }

    @Transactional
    def save(Centre centre) {
        if (centre == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (centre.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond centre.errors, view:'create'
            return
        }

        centre.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'centre.label', default: 'Centre'), centre.id])
                redirect centre
            }
            '*' { respond centre, [status: CREATED] }
        }
    }

    def edit(Centre centre) {
        respond centre
    }

    @Transactional
    def update(Centre centre) {
        if (centre == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (centre.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond centre.errors, view:'edit'
            return
        }

        centre.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'centre.label', default: 'Centre'), centre.id])
                redirect centre
            }
            '*'{ respond centre, [status: OK] }
        }
    }

    @Transactional
    def delete(Centre centre) {

        if (centre == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        centre.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'centre.label', default: 'Centre'), centre.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'centre.label', default: 'Centre'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

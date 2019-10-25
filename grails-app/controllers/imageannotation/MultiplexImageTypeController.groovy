package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.web.context.ServletContextHolder

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class MultiplexImageTypeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond MultiplexImageType.list(params), model:[multiplexImageTypeCount: MultiplexImageType.count()]
    }

    def show(MultiplexImageType multiplexImageType) {
        respond multiplexImageType
    }

    def create() {
        respond new MultiplexImageType(params)
    }

    @Transactional
    def save(MultiplexImageType multiplexImageType) {
        if (multiplexImageType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (multiplexImageType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond multiplexImageType.errors, view:'create'
            return
        }

        multiplexImageType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'multiplexImageType.label', default: 'MultiplexImageType'), multiplexImageType.id])
                redirect multiplexImageType
            }
            '*' { respond multiplexImageType, [status: CREATED] }
        }
    }

    def edit(MultiplexImageType multiplexImageType) {
        respond multiplexImageType
    }

    @Transactional
    def update(MultiplexImageType multiplexImageType) {
        if (multiplexImageType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (multiplexImageType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond multiplexImageType.errors, view:'edit'
            return
        }

        multiplexImageType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'multiplexImageType.label', default: 'MultiplexImageType'), multiplexImageType.id])
                redirect multiplexImageType
            }
            '*'{ respond multiplexImageType, [status: OK] }
        }
    }

    @Transactional
    def delete(MultiplexImageType multiplexImageType) {

        if (multiplexImageType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        multiplexImageType.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'multiplexImageType.label', default: 'MultiplexImageType'), multiplexImageType.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'multiplexImageType.label', default: 'MultiplexImageType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

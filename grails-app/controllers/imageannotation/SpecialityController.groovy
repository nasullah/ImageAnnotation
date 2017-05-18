package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SpecialityController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Speciality.list(params), model:[specialityCount: Speciality.count()]
    }

    def show(Speciality speciality) {
        respond speciality
    }

    def create() {
        respond new Speciality(params)
    }

    @Transactional
    def save(Speciality speciality) {
        if (speciality == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (speciality.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond speciality.errors, view:'create'
            return
        }

        speciality.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'speciality.label', default: 'Speciality'), speciality.id])
                redirect speciality
            }
            '*' { respond speciality, [status: CREATED] }
        }
    }

    def edit(Speciality speciality) {
        respond speciality
    }

    @Transactional
    def update(Speciality speciality) {
        if (speciality == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (speciality.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond speciality.errors, view:'edit'
            return
        }

        speciality.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'speciality.label', default: 'Speciality'), speciality.id])
                redirect speciality
            }
            '*'{ respond speciality, [status: OK] }
        }
    }

    @Transactional
    def delete(Speciality speciality) {

        if (speciality == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        speciality.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'speciality.label', default: 'Speciality'), speciality.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'speciality.label', default: 'Speciality'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

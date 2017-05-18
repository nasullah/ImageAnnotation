package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PathologyImageController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond PathologyImage.list(params), model:[pathologyImageCount: PathologyImage.count()]
    }

    def show(PathologyImage pathologyImage) {
        respond pathologyImage
    }

    def create() {
        respond new PathologyImage(params)
    }

    @Transactional
    def save(PathologyImage pathologyImage) {
        if (pathologyImage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pathologyImage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pathologyImage.errors, view:'create'
            return
        }

        pathologyImage.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'pathologyImage.label', default: 'PathologyImage'), pathologyImage.id])
                redirect pathologyImage
            }
            '*' { respond pathologyImage, [status: CREATED] }
        }
    }

    def edit(PathologyImage pathologyImage) {
        respond pathologyImage
    }

    @Transactional
    def update(PathologyImage pathologyImage) {
        if (pathologyImage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pathologyImage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pathologyImage.errors, view:'edit'
            return
        }

        pathologyImage.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'pathologyImage.label', default: 'PathologyImage'), pathologyImage.id])
                redirect pathologyImage
            }
            '*'{ respond pathologyImage, [status: OK] }
        }
    }

    @Transactional
    def delete(PathologyImage pathologyImage) {

        if (pathologyImage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        pathologyImage.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'pathologyImage.label', default: 'PathologyImage'), pathologyImage.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'pathologyImage.label', default: 'PathologyImage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

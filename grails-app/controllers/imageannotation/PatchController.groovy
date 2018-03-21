package imageannotation

import grails.web.context.ServletContextHolder

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class PatchController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Patch.list(params), model:[patchCount: Patch.count()]
    }

    def show(Patch patch) {
        respond patch
    }

    def create() {
        respond new Patch(params)
    }

    @Transactional
    def savePatch(){
        def dirPath = ServletContextHolder.servletContext.getRealPath('assets/attachments/Prostate_Cancer_Annotations/4463153D_349/4463153D_patch')
        def dir = new File(dirPath).list()
        dir.each {item ->
            def parts = item.toString().split('&&')
            def xCoordinate = parts[1].split('_')[1]
            def yCoordinate = parts[2].split('_')[1].split("\\.")[0]
            def patchPath = 'attachments/Prostate_Cancer_Annotations/4463153D_349/4463153D_patch/' + item
            def pathologyImage = PathologyImage.findByImageIdentifier('4463153D')
            def patchInstance = new Patch()
            patchInstance.iterationNumber = '1'
            patchInstance.patchName = item
            patchInstance.patchPath = patchPath
            patchInstance.pathologyImage = pathologyImage
            patchInstance.xCoordinate = xCoordinate
            patchInstance.yCoordinate = yCoordinate
            patchInstance.save failOnError: true
        }
        redirect action: "index", method: "GET"
    }

    @Transactional
    def save(Patch patch) {
        if (patch == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (patch.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patch.errors, view:'create'
            return
        }

        patch.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'patch.label', default: 'Patch'), patch.id])
                redirect patch
            }
            '*' { respond patch, [status: CREATED] }
        }
    }

    def edit(Patch patch) {
        respond patch
    }

    @Transactional
    def update(Patch patch) {
        if (patch == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (patch.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patch.errors, view:'edit'
            return
        }

        patch.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'patch.label', default: 'Patch'), patch.id])
                redirect patch
            }
            '*'{ respond patch, [status: OK] }
        }
    }

    @Transactional
    def delete(Patch patch) {

        if (patch == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        patch.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'patch.label', default: 'Patch'), patch.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'patch.label', default: 'Patch'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

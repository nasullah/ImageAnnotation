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
        if (!request.getFile('csvFile')?.originalFilename) {
            flash.message = "Please choose a file"
            redirect(uri: "/patch/create")
        }else{
            request.getFile('csvFile').inputStream.splitEachLine(',')
                    { fields ->
                        def folderPath = fields[0].trim()
                        def patchFolderPath = fields[1].trim()
                        def patchIterationNumber = fields[2].trim()
                        if (folderPath && patchFolderPath){
                            println(folderPath)
                            println(patchFolderPath)
                            def dirPath = ServletContextHolder.servletContext.getRealPath(folderPath)
                            def dir = new File(dirPath).list()
                            dir.each {item ->
                                def parts = item.toString().split('&&')
                                def imageLevel = parts[1].split('_')[1]
                                def imageSize = parts[2].split('_')[1]
                                def confidence = parts[3].split('_')[1]
                                def xCoordinate = parts[4].split('_')[1]
                                def yCoordinate = parts[5].split('_')[1].split("\\.")[0]
                                def patchPath = patchFolderPath + item
                                def pathologyImage = PathologyImage.findByImageIdentifier(parts[0].split('_')[0])
                                def patchInstance = new Patch()
                                patchInstance.iterationNumber = patchIterationNumber
                                patchInstance.patchName = item
                                patchInstance.patchPath = patchPath
                                patchInstance.pathologyImage = pathologyImage
                                patchInstance.xCoordinate = xCoordinate
                                patchInstance.yCoordinate = yCoordinate
                                patchInstance.imageLevel = imageLevel
                                patchInstance.imageSize = imageSize
                                patchInstance.confidence = confidence
                                patchInstance.save failOnError: true
                            }
                        }
                    }
            redirect action: "index", method: "GET"
        }
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

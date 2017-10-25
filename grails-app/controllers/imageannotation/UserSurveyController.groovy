package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional(readOnly = true)
class UserSurveyController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond UserSurvey.list(params), model:[userSurveyCount: UserSurvey.count()]
    }

    def show(UserSurvey userSurvey) {
        respond userSurvey
    }

    def create() {
        respond new UserSurvey(params)
    }

    def thankYou() {

    }

    @Transactional
    def save(UserSurvey userSurvey) {

       def currentUser = springSecurityService.currentUser.username
        if (currentUser && params.easeOfUse && params.prepareToUseInFuture) {
            def forename = currentUser?.toString()?.split("\\.")[0]
            def surname = currentUser?.toString()?.split("\\.")[1]
            def expert = Expert.createCriteria().get {
                and {
                    eq("givenName", forename, [ignoreCase: true])
                    eq("familyName", surname, [ignoreCase: true])
                }
            }

            userSurvey.annotator = expert
            userSurvey.save flush:true
            redirect(controller:'userSurvey',action: 'thankYou')
        }else {
            respond userSurvey.errors, view:'create'
        }

    }

    def edit(UserSurvey userSurvey) {
        respond userSurvey
    }

    @Transactional
    def update(UserSurvey userSurvey) {
        if (userSurvey == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (userSurvey.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond userSurvey.errors, view:'edit'
            return
        }

        userSurvey.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'userSurvey.label', default: 'UserSurvey'), userSurvey.id])
                redirect userSurvey
            }
            '*'{ respond userSurvey, [status: OK] }
        }
    }

    @Transactional
    def delete(UserSurvey userSurvey) {

        if (userSurvey == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        userSurvey.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'userSurvey.label', default: 'UserSurvey'), userSurvey.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userSurvey.label', default: 'UserSurvey'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

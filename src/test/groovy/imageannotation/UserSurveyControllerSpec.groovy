package imageannotation

import grails.test.mixin.*
import spock.lang.*

@TestFor(UserSurveyController)
@Mock(UserSurvey)
class UserSurveyControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.userSurveyList
            model.userSurveyCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.userSurvey!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def userSurvey = new UserSurvey()
            userSurvey.validate()
            controller.save(userSurvey)

        then:"The create view is rendered again with the correct model"
            model.userSurvey!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            userSurvey = new UserSurvey(params)

            controller.save(userSurvey)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/userSurvey/show/1'
            controller.flash.message != null
            UserSurvey.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def userSurvey = new UserSurvey(params)
            controller.show(userSurvey)

        then:"A model is populated containing the domain instance"
            model.userSurvey == userSurvey
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def userSurvey = new UserSurvey(params)
            controller.edit(userSurvey)

        then:"A model is populated containing the domain instance"
            model.userSurvey == userSurvey
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/userSurvey/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def userSurvey = new UserSurvey()
            userSurvey.validate()
            controller.update(userSurvey)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.userSurvey == userSurvey

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            userSurvey = new UserSurvey(params).save(flush: true)
            controller.update(userSurvey)

        then:"A redirect is issued to the show action"
            userSurvey != null
            response.redirectedUrl == "/userSurvey/show/$userSurvey.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/userSurvey/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def userSurvey = new UserSurvey(params).save(flush: true)

        then:"It exists"
            UserSurvey.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(userSurvey)

        then:"The instance is deleted"
            UserSurvey.count() == 0
            response.redirectedUrl == '/userSurvey/index'
            flash.message != null
    }
}

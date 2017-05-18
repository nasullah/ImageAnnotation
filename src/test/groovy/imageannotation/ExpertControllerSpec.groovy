package imageannotation

import grails.test.mixin.*
import spock.lang.*

@TestFor(ExpertController)
@Mock(Expert)
class ExpertControllerSpec extends Specification {

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
            !model.expertList
            model.expertCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.expert!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def expert = new Expert()
            expert.validate()
            controller.save(expert)

        then:"The create view is rendered again with the correct model"
            model.expert!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            expert = new Expert(params)

            controller.save(expert)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/expert/show/1'
            controller.flash.message != null
            Expert.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def expert = new Expert(params)
            controller.show(expert)

        then:"A model is populated containing the domain instance"
            model.expert == expert
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def expert = new Expert(params)
            controller.edit(expert)

        then:"A model is populated containing the domain instance"
            model.expert == expert
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/expert/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def expert = new Expert()
            expert.validate()
            controller.update(expert)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.expert == expert

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            expert = new Expert(params).save(flush: true)
            controller.update(expert)

        then:"A redirect is issued to the show action"
            expert != null
            response.redirectedUrl == "/expert/show/$expert.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/expert/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def expert = new Expert(params).save(flush: true)

        then:"It exists"
            Expert.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(expert)

        then:"The instance is deleted"
            Expert.count() == 0
            response.redirectedUrl == '/expert/index'
            flash.message != null
    }
}

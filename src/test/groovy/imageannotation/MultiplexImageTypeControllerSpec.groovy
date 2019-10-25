package imageannotation

import grails.test.mixin.*
import spock.lang.*

@TestFor(MultiplexImageTypeController)
@Mock(MultiplexImageType)
class MultiplexImageTypeControllerSpec extends Specification {

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
            !model.multiplexImageTypeList
            model.multiplexImageTypeCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.multiplexImageType!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def multiplexImageType = new MultiplexImageType()
            multiplexImageType.validate()
            controller.save(multiplexImageType)

        then:"The create view is rendered again with the correct model"
            model.multiplexImageType!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            multiplexImageType = new MultiplexImageType(params)

            controller.save(multiplexImageType)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/multiplexImageType/show/1'
            controller.flash.message != null
            MultiplexImageType.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def multiplexImageType = new MultiplexImageType(params)
            controller.show(multiplexImageType)

        then:"A model is populated containing the domain instance"
            model.multiplexImageType == multiplexImageType
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def multiplexImageType = new MultiplexImageType(params)
            controller.edit(multiplexImageType)

        then:"A model is populated containing the domain instance"
            model.multiplexImageType == multiplexImageType
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/multiplexImageType/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def multiplexImageType = new MultiplexImageType()
            multiplexImageType.validate()
            controller.update(multiplexImageType)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.multiplexImageType == multiplexImageType

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            multiplexImageType = new MultiplexImageType(params).save(flush: true)
            controller.update(multiplexImageType)

        then:"A redirect is issued to the show action"
            multiplexImageType != null
            response.redirectedUrl == "/multiplexImageType/show/$multiplexImageType.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/multiplexImageType/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def multiplexImageType = new MultiplexImageType(params).save(flush: true)

        then:"It exists"
            MultiplexImageType.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(multiplexImageType)

        then:"The instance is deleted"
            MultiplexImageType.count() == 0
            response.redirectedUrl == '/multiplexImageType/index'
            flash.message != null
    }
}

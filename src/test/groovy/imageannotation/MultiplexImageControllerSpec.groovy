package imageannotation

import grails.test.mixin.*
import spock.lang.*

@TestFor(MultiplexImageController)
@Mock(MultiplexImage)
class MultiplexImageControllerSpec extends Specification {

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
            !model.multiplexImageList
            model.multiplexImageCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.multiplexImage!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def multiplexImage = new MultiplexImage()
            multiplexImage.validate()
            controller.save(multiplexImage)

        then:"The create view is rendered again with the correct model"
            model.multiplexImage!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            multiplexImage = new MultiplexImage(params)

            controller.save(multiplexImage)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/multiplexImage/show/1'
            controller.flash.message != null
            MultiplexImage.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def multiplexImage = new MultiplexImage(params)
            controller.show(multiplexImage)

        then:"A model is populated containing the domain instance"
            model.multiplexImage == multiplexImage
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def multiplexImage = new MultiplexImage(params)
            controller.edit(multiplexImage)

        then:"A model is populated containing the domain instance"
            model.multiplexImage == multiplexImage
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/multiplexImage/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def multiplexImage = new MultiplexImage()
            multiplexImage.validate()
            controller.update(multiplexImage)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.multiplexImage == multiplexImage

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            multiplexImage = new MultiplexImage(params).save(flush: true)
            controller.update(multiplexImage)

        then:"A redirect is issued to the show action"
            multiplexImage != null
            response.redirectedUrl == "/multiplexImage/show/$multiplexImage.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/multiplexImage/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def multiplexImage = new MultiplexImage(params).save(flush: true)

        then:"It exists"
            MultiplexImage.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(multiplexImage)

        then:"The instance is deleted"
            MultiplexImage.count() == 0
            response.redirectedUrl == '/multiplexImage/index'
            flash.message != null
    }
}

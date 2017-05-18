package imageannotation

import grails.test.mixin.*
import spock.lang.*

@TestFor(AnnotationController)
@Mock(Annotation)
class AnnotationControllerSpec extends Specification {

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
            !model.annotationList
            model.annotationCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.annotation!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def annotation = new Annotation()
            annotation.validate()
            controller.save(annotation)

        then:"The create view is rendered again with the correct model"
            model.annotation!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            annotation = new Annotation(params)

            controller.save(annotation)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/annotation/show/1'
            controller.flash.message != null
            Annotation.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def annotation = new Annotation(params)
            controller.show(annotation)

        then:"A model is populated containing the domain instance"
            model.annotation == annotation
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def annotation = new Annotation(params)
            controller.edit(annotation)

        then:"A model is populated containing the domain instance"
            model.annotation == annotation
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/annotation/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def annotation = new Annotation()
            annotation.validate()
            controller.update(annotation)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.annotation == annotation

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            annotation = new Annotation(params).save(flush: true)
            controller.update(annotation)

        then:"A redirect is issued to the show action"
            annotation != null
            response.redirectedUrl == "/annotation/show/$annotation.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/annotation/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def annotation = new Annotation(params).save(flush: true)

        then:"It exists"
            Annotation.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(annotation)

        then:"The instance is deleted"
            Annotation.count() == 0
            response.redirectedUrl == '/annotation/index'
            flash.message != null
    }
}

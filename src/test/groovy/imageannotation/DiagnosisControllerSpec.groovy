package imageannotation

import grails.test.mixin.*
import spock.lang.*

@TestFor(DiagnosisController)
@Mock(Diagnosis)
class DiagnosisControllerSpec extends Specification {

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
            !model.diagnosisList
            model.diagnosisCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.diagnosis!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def diagnosis = new Diagnosis()
            diagnosis.validate()
            controller.save(diagnosis)

        then:"The create view is rendered again with the correct model"
            model.diagnosis!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            diagnosis = new Diagnosis(params)

            controller.save(diagnosis)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/diagnosis/show/1'
            controller.flash.message != null
            Diagnosis.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def diagnosis = new Diagnosis(params)
            controller.show(diagnosis)

        then:"A model is populated containing the domain instance"
            model.diagnosis == diagnosis
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def diagnosis = new Diagnosis(params)
            controller.edit(diagnosis)

        then:"A model is populated containing the domain instance"
            model.diagnosis == diagnosis
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/diagnosis/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def diagnosis = new Diagnosis()
            diagnosis.validate()
            controller.update(diagnosis)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.diagnosis == diagnosis

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            diagnosis = new Diagnosis(params).save(flush: true)
            controller.update(diagnosis)

        then:"A redirect is issued to the show action"
            diagnosis != null
            response.redirectedUrl == "/diagnosis/show/$diagnosis.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/diagnosis/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def diagnosis = new Diagnosis(params).save(flush: true)

        then:"It exists"
            Diagnosis.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(diagnosis)

        then:"The instance is deleted"
            Diagnosis.count() == 0
            response.redirectedUrl == '/diagnosis/index'
            flash.message != null
    }
}

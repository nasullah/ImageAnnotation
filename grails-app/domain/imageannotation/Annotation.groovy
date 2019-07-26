package imageannotation

class Annotation {

    static auditable = true
    static mapping = {
        annotationData type: "text"
        comment type: "text"
    }

    static belongsTo = [multiplexImage:MultiplexImage]
    static constraints = {
        multiplexImage()
        annotationData(widget: 'textarea')
        imageAnnotator()
        comment(nullable: true)
        stage(nullable: true)
        status(nullable: true)
        dcisStatus(nullable: true)
    }

    String annotationData
    Expert imageAnnotator
    String comment
    String stage
    String status
    String dcisStatus

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Annotator: ${imageAnnotator}, Id: ${id} ";
    }
}

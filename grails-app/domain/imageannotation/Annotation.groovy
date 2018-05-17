package imageannotation

class Annotation {

    static auditable = true
    static mapping = {
        annotationData type: "text"
    }

    static belongsTo = [multiplexImage:MultiplexImage]
    static constraints = {
        multiplexImage()
        annotationData(widget: 'textarea')
        imageAnnotator()
    }

    String annotationData
    Expert imageAnnotator

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Annotator: ${imageAnnotator}, Id: ${id} ";
    }
}

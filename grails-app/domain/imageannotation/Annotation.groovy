package imageannotation

class Annotation {

    static auditable = true
    static mapping = {
        annotationData type: "text"
    }

    static belongsTo = [pathologyImage:PathologyImage]
    static constraints = {
        pathologyImage(nullable: true)
        annotationData()
        imageAnnotator(nullable: true)
    }

    String annotationData
    Expert imageAnnotator

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Annotator: ${imageAnnotator}";
    }
}

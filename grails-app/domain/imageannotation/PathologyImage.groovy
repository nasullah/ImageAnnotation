package imageannotation

class PathologyImage {

    static auditable = true
    static belongsTo = [multiplexImage:MultiplexImage]
    static hasMany = [patches:Patch]
    static constraints = {
        multiplexImage()
        imageIdentifier()
        takenBy(nullable: true)
        takenDate(nullable: true)
        imageType()
        imagePath(nullable: true)
        annotationTask(nullable: true)
    }

    String imageIdentifier
    String takenBy
    Date takenDate
    ImageType imageType
    String imagePath
    AnnotationTask annotationTask

    /*
    * Methods of the Domain Class
    */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Image Type: ${imageType}, Image Identifier: ${imageIdentifier}";
    }
}

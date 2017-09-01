package imageannotation

class PathologyImage {

    static auditable = true
    static hasMany = [annotations:Annotation]
    static belongsTo = [multiplexImage:MultiplexImage]
    static constraints = {
        multiplexImage()
        uniqueIdentifier(unique: true)
        takenBy(nullable: true)
        takenDate(nullable: true)
        imageType()
        imagePath(nullable: true)
        annotationTask()
    }

    String uniqueIdentifier
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
        return "Image Type: ${imageType}, Image Identifier: ${uniqueIdentifier}";
    }
}

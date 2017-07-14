package imageannotation

class PathologyImage {

    static hasMany = [annotations:Annotation]
    static belongsTo = [multiplexImage:MultiplexImage]
    static constraints = {
        multiplexImage()
        uniqueIdentifier(unique: true)
        takenBy(nullable: true)
        takenDate(nullable: true)
        imageType()
        imagePath()
        annotationTask()
    }

    String uniqueIdentifier
    String takenBy
    Date takenDate
    ImageType imageType
    String imagePath
    AnnotationTask annotationTask
}

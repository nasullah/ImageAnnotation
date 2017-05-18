package imageannotation

class PathologyImage {

    static hasMany = [annotations:Annotation]
    static belongsTo = [study:Study]
    static constraints = {
        study()
        uniqueIdentifier(unique: true)
        takenBy(nullable: true)
        takenDate(nullable: true)
        imageType()
        imagePath()
    }

    String uniqueIdentifier
    String takenBy
    Date takenDate
    ImageType imageType
    String imagePath
}

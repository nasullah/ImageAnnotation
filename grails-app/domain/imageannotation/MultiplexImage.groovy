package imageannotation

class MultiplexImage {

    static belongsTo = [study:Study]
    static hasMany = [pathologyImages:PathologyImage]
    static constraints = {
        study()
        multiplexImageIdentifier()
        multiplexImagePath()
    }

    String multiplexImageIdentifier
    String multiplexImagePath
}

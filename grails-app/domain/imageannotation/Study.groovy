package imageannotation

class Study {

    static hasMany = [multiplexImages:MultiplexImage]
    static constraints = {
        studyName()
    }

    String studyName
}

package imageannotation

class Study {

    static hasMany = [pathologyImages:PathologyImage]
    static constraints = {
        studyName()
    }

    String studyName
}

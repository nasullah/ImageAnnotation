package imageannotation

class Study {

    static auditable = true
    static hasMany = [multiplexImages:MultiplexImage]
    static constraints = {
        studyName()
        studyOwner()
        studyType(nullable: true)
    }

    String studyName
    Expert studyOwner
    StudyType studyType

    /*
    * Methods of the Domain Class
    */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${studyName}";
    }
}

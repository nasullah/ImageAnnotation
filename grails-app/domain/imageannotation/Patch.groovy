package imageannotation

class Patch {

    static mapping = {
        patchName type: "text"
        patchPath type: "text"
        xCoordinate type: "text"
        yCoordinate type: "text"
    }
    static auditable = true
    static belongsTo = [pathologyImage:PathologyImage]
    static hasMany = [labels:Label]
    static constraints = {
        patchName()
        patchPath()
        iterationNumber()
        xCoordinate()
        yCoordinate()
        confidence(nullable: true)
        imageLevel(nullable: true)
        imageSize(nullable: true)
    }

    String patchName
    String patchPath
    String iterationNumber
    String xCoordinate
    String yCoordinate
    String confidence
    String imageLevel
    String imageSize

    public String toString() {
        return "Patch Name: ${patchName}, Iteration Number: ${iterationNumber}";
    }
}

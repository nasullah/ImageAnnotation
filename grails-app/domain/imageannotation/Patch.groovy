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
    }

    String patchName
    String patchPath
    String iterationNumber
    String xCoordinate
    String yCoordinate

    public String toString() {
        return "Patch Name: ${patchName}, Iteration Number: ${iterationNumber}";
    }
}

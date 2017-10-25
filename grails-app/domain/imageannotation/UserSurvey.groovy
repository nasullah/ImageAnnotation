package imageannotation

class UserSurvey {

    static auditable = true
    static mapping = {
        feedBackForImprovement type: "text"
        furtherComments type: "text"
    }
    static constraints = {
        easeOfUse()
        prepareToUseInFuture()
        feedBackForImprovement(nullable: true)
        furtherComments(nullable: true)
        annotator()
    }

    String easeOfUse
    String prepareToUseInFuture
    String feedBackForImprovement
    String furtherComments
    Expert annotator
}

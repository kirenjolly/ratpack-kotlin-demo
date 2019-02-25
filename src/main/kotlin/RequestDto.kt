import com.fasterxml.jackson.annotation.JsonProperty

data class BraveRequest(
    @JsonProperty("trace_id")
    var traceid: String?,
    @JsonProperty("trace_name")
    var traceName: String?

)
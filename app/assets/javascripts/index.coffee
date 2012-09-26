$ ->
  $("#newTopicForm").bind "submit", (event) ->
    event.preventDefault()
    $.ajax
      url: "/api" + getCurrentTopicPath()
      type: event.currentTarget.method
      dataType: 'json'
      contentType: 'application/json'
      data: JSON.stringify({name: $("#topicNameInput").val()})
      error: (jqXHR, textStatus, errorThrown) ->
        console.log textStatus
      success: (data, textStatus, jqXHR) ->
        getTopics()
        # todo: navigate to the new topic 
        $("#topicNameInput").val("")
  
  getCurrentTopic()
  
  getTopics()
        
        
getTopics = () ->
  $.get "/api" + getCurrentTopicPath(), (data) ->
    topicList = $("#topicList")
    topicList.empty()
    $.each data, (index, topic) ->
      topicPath = "/" + topic["url-friendly-name"]
      if (topic.path != null)
        topicPath = "/" + topic.path + topicPath
      topicList.append $("<li>").append $("<a>").attr("href", topicPath).text(topic.name)

# get current topic from url
getCurrentTopic = () ->
  if (window.location.pathname == "/")
    window.currentTopic = []
  else
    window.currentTopic = window.location.pathname.split("/").slice(1)

getCurrentTopicPath = () ->
  "/" + window.currentTopic.join("/")

function fetchConversations() {
    $.ajax({
        url: '/messages/conversations',
        type: 'GET',
        success: function(data) {
            $('#conversationList').html($(data).find('#conversationList').html());
        }
    });
}

function fetchMessages() {
    // Extract recipientId from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const recipientId = urlParams.get('recipientId');

    if (recipientId) {
        $.ajax({
            url: '/messages/conversation/' + recipientId,
            type: 'GET',
            success: function(data) {
                $('#messageList').html(data); // Directly use the response HTML
            },
            error: function(xhr, status, error) {
                console.error('Failed to fetch messages:', error);
                $('#messageList').html('<li class="list-group-item text-danger">Failed to load messages.</li>');
            }
        });
    } else {
        console.error('Recipient ID not found in URL');
        $('#messageList').html('<li class="list-group-item text-danger">Recipient ID not found in URL.</li>');
    }
}

$(document).ready(function() {
    setInterval(fetchConversations, 1000); // Fetch new conversations every second
    setInterval(fetchMessages, 1000); // Fetch new messages every second

    // Scroll to the bottom of the chat container when the page loads
    var chatContainer = document.getElementById("chat-container");
    if (chatContainer) {
        chatContainer.scrollTop = chatContainer.scrollHeight;
    }
});

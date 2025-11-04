<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gemini Chat</title>
    <style>
        body {
            font-family: 'Google Sans', sans-serif;
            background-color: #f0f4f9;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            margin: 0;
            color: #3c4043;
        }
        .chat-container {
            background-color: #ffffff;
            border-radius: 16px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 100%;
            max-width: 600px;
            box-sizing: border-box;
        }
        form {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }
        input[type="text"] {
            flex-grow: 1;
            padding: 12px 15px;
            border: 1px solid #dadce0;
            border-radius: 24px;
            font-size: 16px;
            outline: none;
            transition: border-color 0.3s ease;
        }
        input[type="text"]:focus {
            border-color: #1a73e8;
        }
        button {
            background-color: #1a73e8;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 24px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 500;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #174ea6;
        }
        .answer-box {
            background-color: #e8f0fe;
            border-radius: 16px;
            padding: 20px;
            font-size: 16px;
            line-height: 1.6;
            color: #202124;
            white-space: pre-wrap; /* Preserve whitespace and line breaks */
            word-wrap: break-word; /* Break long words */
        }
        .answer-box strong {
            color: #1a73e8;
        }
    </style>
</head>
<body>
    <div class="chat-container">
        <form method="post">
            <input type="text" name="text" placeholder="질문하고 싶은 내용.." value="<%= request.getAttribute("query") != null ? request.getAttribute("query") : "" %>">
            <button type="submit">질문하기</button>
        </form>
        <div class="answer-box">
            <% String answer = (String) request.getAttribute("answer"); %>
            <% if (answer != null && !answer.isEmpty()) { %>
                <strong>🤖 Gemini:</strong> <%= answer %>
            <% } else { %>
                <strong>🤖 Gemini:</strong> 질문을 입력해주세요.
            <% } %>
        </div>
    </div>
</body>
</html>
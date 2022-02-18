<%@ Page Language="C#"
    AutoEventWireup="true"
    CodeBehind="Default.aspx.cs"
    Inherits="webTestUR._Default" %>

<!DOCTYPE html>
<html>
<head>
    <title>test URL Rewrite</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#cmd_send").click(function () {
                $.ajax({
                    url: "api/json/tacke", 
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({ masv: 'k18123456', email: 'kobit@tnut.edu.vn' }),
                    success: function (result) {
                        $('#ketqua').html("success Data: " + result.ok + " msg=" + result.msg + " msg2=" + result.msg2);
                    },
                    error: function (err) {
                        $('#ketqua').html("err: " + err);
                    }
                }); // ajax call closing
            });
        });
    </script>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <h1>get value for session</h1>
            <asp:TextBox ID="TextBox1" runat="server"></asp:TextBox>
            <asp:Button ID="Button1" runat="server" Text="Button" OnClick="Button1_Click" />
            <hr />
            <div id="ketqua" runat="server"></div>
        </div>
    </form>
    <button id="cmd_send">post json to api</button>
</body>
</html>

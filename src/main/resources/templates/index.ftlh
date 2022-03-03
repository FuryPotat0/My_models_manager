<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Organizer</title>
</head>
<body>
<h1>Organizer</h1>
<h3>Add task to list</h3>
<form action="/add" method="post">
    <table border="1px">
        <tr>
            <td>
                <label>manufacturer_name</label>
                <input type="text" name="manufacturer_name">
            </td>

            <td>
                <input type="submit" value="add">
            </td>

        </tr>

    </table>
</form>
<br>
<h2>Task list(manufacturer count : ${manufacturersSize})</h2>
<table border="1px">
    <tr>
        <td>Id</td>
        <td>name</td>
        <td>Delete from list</td>
    </tr>
    <#list manufacturerList as manufacturer>
    <tr>
        <td>${manufacturer.manufacturer_id}</td>
        <td>${manufacturer.manufacturer_name}</td>
        <td><a href="${'/delete/'+manufacturer.manufacturer_id}">Delete</a></td>
    </tr>
    </#list>
</table>
</body>
</html>
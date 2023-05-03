<html lang="en">
<head>
  <link rel="stylesheet" href="/books.css", type="text/css">
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Caveat:wght@500&family=Podkova&display=swap');
  </style>
</head>
<body>
<body bgcolor=#AFEEEE>
<body text=#000080>
<div class="container">
    <h2>BOOKS</h2>
    <ul class="responsive-table">
        <li class="table-header">
            <div class="col col-1">ID</div>
            <div class="col col-2">NAME</div>
            <div class="col col-3">GENRE</div>
        </li>
        <#list books as book>
            <li class="table-row">
                <div class="col col-1">${book.id}</div>
                <div class="col col-2">${book.name}</div>
                <div class="col col-3">${book.genre}</div>
            </li>
        </#list>
    </ul>
</div>
</body>
</html>

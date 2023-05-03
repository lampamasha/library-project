<html lang="en">
<head>
  <link rel="stylesheet" href="/authors.css", type="text/css">
<style>
  @import url('https://fonts.googleapis.com/css2?family=Caveat:wght@500&family=Podkova&display=swap');
</style>
</head>
<body>
<body bgcolor=#FFFACD>
<body text=#000080>
<div class="container">
    <h2>AUTHORS</h2>
    <ul class="responsive-table">
        <li class="table-header">
            <div class="col col-1">ID</div>
            <div class="col col-2">NAME</div>
            <div class="col col-3">SURNAME</div>
        </li>

        <#list authors as author>
            <li class="table-row">
                <div class="col col-1">${author.id}</div>
               <div class="col col-2">${author.name}</div>
                <div class="col col-3">${author.surname}</div>

            </li>
        </#list>
    </ul>
</div>
</body>
</html>
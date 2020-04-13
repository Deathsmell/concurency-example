<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Пример страницы</title>
</head>
<body>
<div>
    result:
        <#if result??>
            <#list result as result>
                ${result},
            </#list>
        </#if>
    <br/>
    <div id="count">
        <#if count??>
            total count: ${count}
            <#else >
                total count: 0
        </#if>
    </div>
    <div id="cachcount">
        <#if cacheCount??>
            total count: ${cacheCount}
        </#if>
    </div>
    <br/>
    <h3>
        <form action="/example/1" method="get">
            <input placeholder="Введите число" name="number" id="number"/>
        </form>
    </h3>
</div>
</body>
</html>
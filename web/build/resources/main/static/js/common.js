
function sleep(d)
{
    for(var now = Date.now(); Date.now() - now <= d;);
}


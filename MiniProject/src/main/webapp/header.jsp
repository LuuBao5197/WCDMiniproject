<%-- 
    Document   : header
    Created on : Mar 15, 2025, 11:33:27 AM
    Author     : Luu Bao
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fahasa Book</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <link href="public/layoutPublisher.css" rel="stylesheet"/>
    </head>
    <body>
        <div class="container bg-light text-dark">
            <div class="row header">
                <div class="col-md-3">
                    <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAABDlBMVEX////IISj8///EAAD//v/GAAD5///CAADIHybIHSTIDRjIABPGAAn9//38/v/s2tnWkpX07fHLNz3HDxzdsK/HGCDZpKrw1dTDABD6+Pj28vHFAAe8AADz5OXjtrfkvr7Pc3fJR0v69fbJLDXr1dXx6OXmxcjGKDDOQkfQYmXaoaPkurvHQUfNTVHPR0jeqqvRfIDYi4vSbW/nycjQXWHXl5vWhojmvcHq1tTRb3DQeXrJTE3epKLckZfdqKXQhoLKUlrZg43JaWXy3OHet7HbrLPISVPGKzfemaHlycLu4NvLY13RODzSd3LIXWXOdn3Qlo/PWVbHZ23Vkp3LNzHIQzzUhY7AV1zZhoHSQk0s/k6LAAAfO0lEQVR4nO19e5+aSBpuUQpCAXIpKwIG8MJFUBsF0dbWHjNO0pneTLLJ2c3MfP8vcqpQO+nMbeec2dizP58/utUCrIe36r3VWwjABRdccMEFF1xwwQUXXHDBBRdccMEFF1xwwQUXXHDBBRdccMEFF1xwwQUXXHAeNCHoh65hn7sf/z309dmAdIh77n781YBFk/6p01c6ib8ZKh50HfY5+/TMXftrAJtjs/RC9tLMWiaaw++ftxk19TvnzF37/0fIzbcjASZ4aFktSucDLId8GSL5lrUKybz5dxci7A71XQkyZZyaXBeCwBnzuyLGV/9i0hNiXq8OU1XwN9Q+I5+KRw0aeejajjTw7tOxYAtdLjXWstmttSl9rcE1qsELoR+Pzt3hP40EvwuBCqeDZwYEK75WCh+DFgghcBvmLhi7AAbS8Gdr7YA61CZ4oJ+7w38ak4lINja053EZqcI34vOiGFcjUVOkrJlTzbpASUL4AsCNMsjjvx/DZPhip5QQ1IvvQkonbGvjigQMJc7VAipL86o1x3KbqqNhmiGme1Qo/I00z1we+QkO+/Bk9DI+Uuk/VegmRp1ax41iwisRu2A9XBg7ecGOcTZv/yYGBDab77lvw9d4s9+1HUcQ6GQbyxoQmpkN3IRSgWDDt9yVeQNAb+cWpuwBGC0sPj13138f9dMLZyps0EDbT2YGwnfxlPbbwUgD2uI5VTEhWM4hWFqtIv3XCoDuoj8dDwy4x7kYP3GHTjhSFHZD4MTv9aAVwQlZ5XFjC10LaVrDrDEK6nu+gE7P3Ra8DoHmaIvGCMLdsykh2lkJ/BHs0oZszkGdN20Q4FkfasBTuDTwBpory8E03fE+PeDWmr0F8Lu97qfVNHXHBQB73b6R30E2EqB6biq/Abth/lTQLrYGQ6UEwNjYwszVYnkwHV0XhiW3fPdnnpp6sOKK2HDAy9OQ7DMVqoGpLDMfHdjhPniiLk48eF/b2iCZTdCi0qAwXQB3hea3Lf+aoBl8yRGmK9HCLVvQ8Q9q1h0JTSo0qL5SzBC6xesJaSyeqNWYyqNp7INdsZbXNpMG8Ewbqu1Eh7Ae4xy+6kSs63e+s/8oqMJhMPr864rPs0buQOdnhDExn6IIVToFvdqd9mwEumqOOIcxhC/l8HTAkNy83BrVS68rPMsezkxxJ6XDtHhOP4I5EqW5taQfP+t/bQp/ABiEKizlsePQgHelrEAloI1F51rd3bs2mBDuoIgoIme3fDhzHZfPS1jvcYYKCkvCi3mDmpR+Z3sWHr8NJ/boX5eNQgi9xlHrr3B4n/diyzRAj5BP/ooqRsdXdTBWio93dtHpCSDM0ST3J/RsuLXQE7ON3yivbDpUbciivVI/Cis2+5PhYiIXUOUk6RNDl3rcVIeyM0ASvxO27kLJAdD57c7J0Bw0Pdk09aekbuoOMeUF1Q/pxDcEEByHY8Hn9of7BPcA/F4mvQefB7RrjOEetehx3V6qhe4EremgnniZ26ttoF8jk2R3Hi6/AsYmwl0sUxfslrfivJxvXepbQ7CuvYb+/ZCG8v0JkZlMYBCki/Wzouaz+al01k2Q1oIQhBJ6QUXrL9wZ5lyoo2HepUO7/hTEqLpUQ6ielcZcbINI4WLCI4t7BgE0YiWAwnjgQWOOcEzDX6DLRJHxfdFgujRQTGUK7HHPBm1L3oImdFNjPNQgWPB6miMP2O1z02Mot1BwzDibDiQDhIooJdMBGtaoDcxqg2bdpkPW5zC6C6gEb3mJTNCIjlIaLcHM4rrUjTO6PzqZjGb0WlGh+2wsbxuBkEpQu+qemRwDXHdoSPTTTnN3IjVh/yAc6i2n67KxFCZWDtQm9WosbOWOQD0zTsKzVyL1tpU5Vb9zglJpQtWv2pxixKxDZB+iyaUJs144qg275yV3QIKULuzPF81RzOYZEsU3ZjRysthFMjPiWYywmFUJUsvM0yge0/ErUbfF50Vuu8Ixa+mRKsN48A9guNrYgVBYV+X6nMyOUHdzGWWqWgRwxJTqt0Q0ZwWEUF9jahOEqSLJkxAUHgTOc2+qDRFToyZyoVbDVNbvCA0l3OecUhwv6Ppd0mNu22TtvZ6dkdkJ9t3tnPAPTliIJU5Sat8YgrDDyjVcN0RlAWFgzamh9PQwk8w+8874EVTv0NrvT98CIRhIIgnaXrl4a6IGz++Yd2dLrlNSExqe230TyrVhoWo0sQhBKC1JTMpnqUNDQ2VUIAlT9qHVHduw5WbL5zvmqsCU/wbCsrMTjIHrzJAkchypKUpHURRx3joYznkdOADau+L3vv4rQLX/Yc/i5PjObQozLJLBamkDt4F3OVFSqmK6HT/eww+RlhWHsKHgJ03oxAlMi9ZAljiRIB4Pk2nZaoenhNs984GiKb/81e/9mggDkB4YqmEcwJki0g6L45bwDyJR40A/tztzY9r/PtceDLitEKpVRjr0byw6rDGZvA6cQzQF3enxIOo1eDxS/K9N6BegMnIOOTJ7rqyh+gPtMzfXl06BRE5mEqjH3eY1zNNPq2nwqkYboD1rEOqwmt3RvXMKPNzx86N7V3c8EQ/M6Msv/IpQQdhuO1AtihA0qRedKiQ2YP12oGCC0NyfI86qZpGeqbB4/nmaacFWZZzEEjlkzodxLFupINCwJNxwRD4d6NWwWV49+/rEToBqFPPJ1AfXbzF1MEFITJOGERD0W/PYspBJpyTaMNE4L6E9nAifrYhm/A6EPUT1bn77TAt+FBEaf4zcFkbExAULUEBodBHvRzXjfAxTC796F0CmOxb8t4Iw5b05umIdoubQ7tNIakW1jlPl04S3NR+C0Hb929e67sCoxrkx5kRLp+dDo0gXO0mupXd05i74NoDNYteIJlwCEu98BKnb1SFvktmycENhzPseb9oT0gmqtmlEewn3NQ51GUNjzu9gf4oGHV5B8tABbUUyCdVIG6EPta2+uMJEsspUJvMf3jWokViZU8XhMiPI/qAT/1UIMEOIeyOa8T0Nm/IpV8Ih4SvFUDfR2qXSzGUR0/jgvmfJbWeIYl5KqKxor9sKR/URMaMgy2e3OdVJRMyECb9YR2RHw47lfhwLazrgz0mQAo4SXlFW1HBp/LCXhg73npovqgrtmFqAuKv2TQl3wXWM5alws4gWhe0OMUul+RbHcVK8j/khjawGnITmDrQbb0NjM0Eujcl0LoNQy+9eLdlSq9A8F0MArwMHUnvRt7jeHhTdGb+t1MkEv0mI1RM8mRvcIkLi5l6jOkh4SYjFpDxDlKHiAWdVU+jcI/FNAZquXre1Lm7RCztrs+m84KXMXr5Is6J9tjUp9eSA9GXy0Yb/NLrKtPpkRkhvMxwu7FgUabhRudsgTHvUPHCAWUg2RlleAxbdIRf3jq6ZrQ+GsyLSWvM8KrnaioYoJr0XsXK+tFRUFipzSPuyEkEa7Q75g+7TsTiZZXvJyAkVFppVaxFOMRuIMnNZUpl+annVzYC24RwSjW45NwmReV6Ohz/LjUkBMzHvLjc5OkMS/FQn8rKmxKuQjVKZUdNq3CHGY7pj1gTpckNFQMZ9YBfeJomJiHNm6ipXe2h/1m8IrhOsdMRJt5vPJzc3qzIQoP3hh9Bt9VB+hnnoTpcadSRhn6zS4Q8h7GN6n5uwWztq90KUhru17YxGMifKbOa52VsiiZgaDwjWbBZa5eliLChxujVZGbdsaASnhWMV2tF1tDaRaZ+heirEZifuecwerDXnGtocHtKg6PmzY1ecNxK3WrrQoaqGJH19Ou0mJifJHFMZ15iKUIz77Szy0tSj8QaMTKTceDYIy7zRS2bXh8s0oavv+J0mfHV+9B7P4+48jhfU+5+4tDvCULLogDLAZrpnN1xdUWaYOuSZzClLYLeGNLLlyYIRhAmmIiTdnFdqtQ6qDfpCiuRBlYOqg2ibmEjOj9/jbLbReSwF1KxEy2a1FHZla+awBL5cUnUB81pjRu2dYM9l6efF90BHouUwN65/XbiwUiiFQglyePCie7PIqE0pgF7Dk1NwEUSjn8hDyKTSS/b79d/sx38TY5K4++QK3Mf8eu/ATJYGzENdYSyP5y5U7QVVHItmQqS7Q+DXPwS/KugxEaKFDR3gTEmnhBlPxgZ1ZR0QzaJioMi760/fE3V7fM+FZxiofo1LXG/BsoV8HAku4SwW5/ykTGOCuIDee62cc4kpSb1qpQ1Mu6B64XeoLZRMKrNQFxFOYIQlqQ2KZ8633devGkhSXjlHzUJvTdMVinxIdmeIg4XMWn3n7FmfV3gwbS5obO+yvP47XyaEazE2gu0OKMPqeFfiW9XC8IRaSJFaT3/VS2Kp5vVNgkrakpArmZeRshupp5kH7aYWlj9ciYp5FpPv6tnJo3Gpuh8jlEPocmIzmXBiVXdIQ2QWwlcdLmVJZLZySd0aEc/YrLveyVIL6NT6tzZp20h749li5jVB/YHh99KO9N4gZXYmD5xG5Z8MVTM0cacF3UHt2vcSIlqV6XdiSSRsAmoDK6ttoQoTwlzuUbvd+oeljAMQDOiglWXUMVPm2sDHlq99xSv8pIRfW5/WQzaQ6iyKKLZhNfboHzcm5F6dzFO72VIktGDKoT8QuY5GVaIuW857ywcB80xrhf6cGg/e1NwEUYKcyCINdPdQg+m63nQdBUEWwaDlncPtnm1OrwoeDReLBVN+sJDx0HFf2U0YxnNsViOLzrrKn+viRuDjGdW11BQmghBm2zSwX1jM+J8g1tYHfetbSLZ4hJRG0ITnKa+Z8uvvDwYstKQ3b3i5M/X7gAoKzZvFrQr660jGA3bvaZiEcyrDVyhJhXThsFkoV5EEBIEpV/xECWNSvUI3IR2QthmvuFWZD/BBSQl07H5ta7FAZm3iN+kobVIhrcsYo1oXNu0ekb9thiqwQ1ciCisACnhOJAZ0LPyhAMHLknqk0g0roAFwg0nFD1tXSXKFZeaOI1ODoI3bs9BNLGJV64dOoafe7CsXhGX8/N24MWMussdz6MP+Zm3WqGqJahx636+URdxlqxl1QCNBOjjrnGRpwHCZWsFsiEO4VsQDv7WmQkFws16DCpJwBfBbzWaxuJHkKryyTQW9x/zXnYwwlS39Om+4zDnO0VXqwG8KtjZPTSNH7gJmqv+pNVitN8wUOiwzOJOTDxD41DKISKNnqTlLHIsS6obgoKio9cgxFqXaRhAKk8eSsqXRigqiqaeNlduvSpDOPlMelkZe6b5UluRe27bfsxipoDOL4JLOGmH/3Kn6/Z7qFnlmxOPgHrJ4WLqiH9or5pyKaBiBB0NA51qbDk2x8ZOv8EjGU7vphDbVq+WAz+HXjp+0YbyZ7QP2tcYYUVIj0HJorGMy3S/JN74A24fabXjIi879eApDlmCjISINJFmQL9XWD2NPiLqvkpErRGOF4OGz1rNlUOhXce9DS70VB4vwtzryXwNsDrjld/+H+VKCvbIQohFCQYP0aqGM6hZrqBen6rR7iUoO9drdl16VvMgAnDIJEs7/pCENv/gQIzRs2d5QweR9vov5wcZVoWOrqnqGCJiqb80BxfNXryPXUQP926urGx5Zg/fvB4QwlhJSxPksKzTHFtwbShGP+863THkqBZ2UTII3y2VWGLTzdUGlQbDTdzyTnzhAHSVih+/0WgdD2GyeI7AAzVZpUBdsnzRqSjzsTcYcUay3SwcIy+7YtCyEiUQIkhWLMyd5zgSL5nY1hmsujRmZmbgiimxxqwA4aXhdFrc9gvBWpVel3m00Ch6yOM4m/erFimo4/jGLMlZA4qZzqdHoKLw4vaY6o500Zu2+65fdnRmLGDN1SYVa2T3UZd4Z9boPdp4Tj9YiDyOcJFSsMv/y0dfAeh3CwjXai6GnfuUoWG3POWsGm8tb34ZNR2sXLksnGavGNDBs9z6qvBbbrhdVrCQdXBdy/Hfw0SQJy5ZFnRliLZZz+mZYhlRjqqDOlHITqM3lu3ESOGMywDcj+PXj/L7jtFdovNE/U3M0HDL/1esOk51aJcvUmUwkOb7jZMJ9CSKb3WyZdU1FEhHJ9SxiQxECx79heSg4ckNb8IYD187S4kyV34sG19P3B/f0aK5ge4d5vpGzztahPZepG+YbjhuxtdDPgZW5f7AUjjepUTnieP7DNk1nyb8aRd/z+tCX5uvVavJm4J2vuM3uNnC82y5a1AsLuy8OjqPgaJ57dFEmsii/rxQGFPTOZ/wkZd5WjzdFFVSfyVEkCMkIdeJre53gtQpmDTlGWKnp51qVYfCxjBSZFML9q8E27avFJovae9dx921PHysYo+1J0cMqC3zQMPjO/yQXO6XOq9ej0YUkSQjT+Cmwtby2s+HtEA/fF2et+W7W3Y+TpHQEY4B1N2ip0N2aNb7WsSxFlrGFchb7Hg92KlNRhUj/COHDx6A/LJxbqjIX701zvtVoywrp4UyiN8F261A99+aLSsXB1JwVCV+VaNnXZRI3GnwDzzOWU/x0aLUaw1Vpts+v0LybaB88CJuCbTdtjarm+47yyhw+pR1CcKnccbES203QLyKm8ekwdWxYXEMv+HRYKB6UDX5cjge92t1tDlW93xdUWIw3QCgGPFI646ezJ8Gem/GQy0MotG+sw7xp0ulX9Izi1eclsLuDyUCbR2fXQfsGcwb49yJ3PehMOxso2IXW1uzz6dBfom/0DapGF538OgSFWoQf+0043eQ3u89nkX7QNVb2Zdftez2E39f4yWCz8LbKaU3qKTE84Jvam+7Peney4CY0nLOtuDFzPveZl4eJKP+CIRRgXYCFXpN5jo/lxpPdwdbPEkvhZVkmczpU97l/2KndrB9ownbnC4bBF1TDbTfZlZ/P3acFlvULstf5tu2yuJ3SOmTnR/88VazVDqM0Pb63zfYjimzpqqo2OreB+LNIyaEyTXU6h4zhaZ7Zk7dVnsM+QwT/VwJq80OhBXSq5BqHF6cm26aaVAXt2uPCpyexy+JP4eSVO43KqyH541Z3xT+qXav/7Ubpg9Z3a5UMq6LaT9g8R8qHR594JXyKpuKXcNLHS2JUl1YMpX8/Pqw9SZzP+Tg9VuznvATnWdv+LTx40jByjj3rx+N0N/ys8/7BHorxF4uB14/kpcX8nDqom5tzpJ9+B8HpWQHa4FS1DKeKUpt+Ftxt0DF42j8+9/HiIHR0m4YaZvdJeW0AlKfdhcv5ySMJV0jyPxtpsIsPwcWv1qbDL/xs52mpGwh0JTlEtfBBFarQ8B7VdfWOyRr51x5kYv/4+P3TIki7kyqTfwq/36tT9MTh+a8cGTa6566W/QMEf+guHxUNW9IOfznFYOQ+rXn3JeAfG7DTNGQrUb88WP3qi0t/AVhNMLQNCla7JdyiY6IGDwMITy3gDCvYfxX6Wmsxv8PWc8siN4uoz3bbH3waG7jZaoithmJx4w+l7z4t2/6fANa1bGXKMiYSVS+iKBKsmKXtsQULKXauV1jGrIU2SQQpeLKgseTfY5RWJTYg0HsEkVPy8JQFlgf+FFF7Hy0QFh9lwEWCZC65ZTHU2ZOHfwQI7GIWy4g8zuGfOOYDTppPkPQrjSKRrZvUfdJP4WM10cViYB2WgX8N1aqT+FvNIkcsNNm4NDw8Zx7/d+AU06GFT/IRJQkjZMkUbLn0S1Zs/iH51CqdWFNJ4l15lqrn3wEzBcD1kriiR7uOEXW5yd1k9fF1mi1HXvp6OhEbMvpEHiudu2RbZt7Iy9LXi3wcK4pCqTKmdFKS4aywhSeTr6n399l6wCuk4maRuJfr3rVRVT2drDj972YJOSxuS5ZJbQdLOx3dBBbv2m6ULeZDDlmI7TyVeWuy9d2zZ72hs+7Oh1JHQYit2A/er8tIc35TV9wvCBFF+U32mx2vh4E3y3uixa5oyUr8Ku+e9RFuMGQF+HyD/DtZpIV7jJ9+y3xTTaTdyNZH5zfjhuOZtqEty+kutqqrz//qXv8ZQIfOsygwbOE/VQ12noH/7FioNm23vfSy7/7f+/cXov5JKlAN23v3C3VvG47z8NF/6KI9WU+ueE8V5ZcrDy0Sc092MeLPAQYW9cik2hdLDy1ZQv8rDCeYGg2u8QuGIv4fYWhbItmFYfBFuev/EENDEdE/D2UIVUbqaBuPDFng8EmB1B+lm+pV62M7oj5BN7xvnZaW2M4JQTh6NUeGbCH0k62gjfXPt1cIsPnIktD3T+25u2q5XWCOzLcf9zSK8romJw4mOhudFUMnncSi+UNRCRj0R6w97s3uq1OBu53Qt5PtYQarQCvnscgNcu8JZeCgHSvM8ySIj2BkKiyKpx6mlVYMxdJUaMQvIX7N/NFiXEX5IpZrpdoEqs78URZw1KbMpxNm1Xvqo/PjM1edwE/GnjKsoiSJ1KJrGgNjWVEQ4SSloAxpyEDZdhRqS3i20R5RYaOOYlEWis8e6iqJSO7IrHlB5/FCoeRkhd0F3DtjLKWGQaQ9jCJV6OaJyEnDPNkPKcGbW99Px2xvM2PIiXiyibwd4th+xB3m8DhdLrMeZgliV6GiX3tFK6EUOy4oLFGk7yN/QSROPtfjW1TB+7DbjRto8Zlp6CuVptnzHM4rHWGbojSEjCHSmSxgQilrLhZJUonG7knSG9uTOVTFELCUOesWfINEfHi0kIcP2xrPguL53IbQLfGd+5AnMyhDHbBHA9W+P3yyIpLJZIjnhz13PtW2RdFA7LmCzJ+dYmlgZzKrb2dwGlZjBuaKPKaXpGc4RETne0BN+NZkmfhweGec3Oojw/Z2O6skC8MhJ5lMhvJxJbttcUq0n21nLLMmAGPCZFiwKo2ySii6FGo6m7Wq00ELcWdkSAUyjlh6jRs6xyLlI0MG2y2yxcQiB4YiOm7tpQzlanVNDWn7Dabtb/rqEIscapjTTGMPGqryA/29X64GFndWhhDaOtsNW/Bz+6BSHxhqs56lyEzjHxmevLYjQ/ez9jcO0ExZqnYwdHpptaHdX8UdhWWUz8qQVZ2DfWQDNZG6B5V+ZAg3mHYey3IjvhN/hSHMCJIkah4adwORypCeqJudKldF5LELnUQhLE3MW2PxjAz7gc/2g9hOHfio9fFzhiNeFC065qK98IH8kmFUdKj5u5ve0vY1ZjKkw6EZpN1/sdgLT4REptZyp3ttwz2bphHuc4xRo/GDy6aNQ6KoquU6MGTbtdGxCujbX2OYUytYHrTTmlCGjuEYlS25XyBOtKhiko6ujIvOxTDbZa5haBuzMTNoDJB0oc92gxwYOrFEdsfHkdz8CkNvLElVUQ21Fgll2Nxx3KtDxT9cUFO5oxL2Dj55YJ1plN7Gp0fmzGoD3QbpgLqa0YnhnnpuB4MOfTrffsGwNZTInLWrsCB0nqorLOGD3wCfWaL0nohycWBMpX0WhvU0j01dq1OvVHjWmP7gtdkG0msNHmXInmUZsUeapaTSpRl6xHA0oe7miLVnosRJA5VafDLRmlRs7TvatEEimRs0uHITRHVpKnz9zDDbLecudT1iC/IzZLR15NGISAuPmqZLZ1NnnExES2Kp/DknPp6HbLbJZrITZdYuvU+uCFWiw2RuUvuJPjhY4jCZJ6aFabv0Zrj//e78l8DWwdgWT6BO3tPpk1R1lEeGLqEWnO3owmhHtSOLg44M1bYlypHDNl3SdpFYc2oRUa5xbAc029cnyTcOLDsSa5dEeTykr85UNAzD9iGugG5jA7RGpfuODIX7SUNGSG70Cpso1O7Vasdewnaj1hhBd1dj7bVxZA8UVEvg/YeGYrG1AVI6TUHIEH1nKZ1v+i16oednYAiFkdmg3Zbmsyxy0sY1mO5YwuHBp4Faq0yrX7BwnpWeQZ3Nw1yq2/QluzF7ryy9PT3FbpXVQxOcKCs3L4Ojh2tHaZn67MB2ummfIZVhd8kiCoLllOO7y/R2MrTt3TvhM4aVE/eQhFHV+nH5ulmvswJZtaqSrZZCKye0ykvBo5t0OAU+FLCcow4FfsiPhcvOrNa1gTPMQb+7c+FnnvcDhL9jpUw2O95f+jcYSG3omBMXBAs9Qge/lErCPSQOBcF4CdmPx4BDkpE9q7365cO6ccxOfNqtznS0e5CqWtW0U0lDlqn66kuldjybLWZl5lcawO4+L2FzRvKla7TlimEGR9mPUTOdfacVrj53Xxq+tmgJ7cL3jWVz6gJ3MYI7WLxYjuwWDLdlEXqF4bWM79If/cwNQGr7xU+Rb3u+t/NL/atXumurxSbS3IcMzUu8C6imSNPRy4MM1+lqNXPBfhF0V9usn+Qf9Q+BDr2bd6+3iTFJgZ8Uwc2yawTTtAvW+30525Vut73z8rIY6BkYpz9No3RarhcvFsHb9Kuvkh5WqB9Qh+G00Ztlrdms4BF7uNC6eJdnLjC2mj4r9eDtdvvuQ3unevpd6a3Tn29sd7T6YZJ0Xe9FtAIvomjrrcrlW/VdP/GK2c6rf8yn3WC3iCjDbP/q2VP4rTJnWeovNWiXmw31WK+bRTGPoH3tuJrjp07gfG+UBbh3i2sn8IVrJyyDAhRaGVw3v4f2Jrt3CmMTgaAeZVEQ3sMgDFx931aDdpH4536g9wGH5yk14eExSJDqTxc2myxYYMsTLH3Pnkl7gApVga3xVtqKvWJK6FAK3ayaAWtnv3bC3j7xoswLLrjgggsuuOCCCy644IILLrjgggsuuOCCCy644IILLrjgggsuuOCCCy644IILLrjgggsueFr4v8KXUocbvF0CAAAAAElFTkSuQmCC" width="100px"/>
                </div>
                <div class="col-md-9">
                    <div class="row">
                        <div class="col-md-6 py-3">
                            <h3>PUBLISHER MANAGEMENT</h3>
                        </div>
                        <div class="col-md-6 py-3">
                            <div class="row">
                                <div class="col-md-6">
                                    <img src="https://kynguyenlamdep.com/wp-content/uploads/2022/06/avatar-cute-meo-con-than-chet-700x695.jpg" class="rounded-circle" width="50px"/>
                                    <p>Welcome, ${sessionScope.uLogin.username}!</p>

                                </div>
                                <div class="col-md-6">
                                    <button class="btn btn-info">LOGOUT</button>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

            </div>
            <div class="row wp-content">
                <div class="col-md-3">
                    <nav class="navbar bg-light">
                        <div class="container-fluid">
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                    <a class="nav-link" href="PublisherServlet?action=Upload">Upload book review</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="PublisherServlet">List book review</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="PublisherServlet?action=Rating"> View  Ratings</a>
                                </li>
                            </ul>
                        </div>
                    </nav>
                </div>
                <div class="col-md-9">
                    <jsp:include page="${page}"/>


                </div>
            </div>
            <div class="row footer">
                Footer
            </div>
        </div>

    </body>
</html>

<tr id="${name}_${language}">
    <td>${label}</td>
    <td>${textField(name: nameLabel, class: "input-xlarge")}</td>
    <td>
        <input type="button" class="btn btn-danger" value="${deleteText}" onclick="$('#${name}_${language}').remove();" >
    </td>
</tr>
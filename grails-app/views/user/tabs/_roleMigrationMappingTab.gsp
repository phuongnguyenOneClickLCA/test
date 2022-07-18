<script>
    function addMapping() {
        const targetRole = $("#newTargetRole").children("option:selected").val();
        const username = $("#newUsername").val();

        if(targetRole === "" || username === ""){
            alert("Please fill all values");
            return false;
        }

        $.ajax({
            url: '/app/sec/user/addRoleMigrationMapping',
            data: {
                targetRole: targetRole,
                username: username,
            },
            type: 'POST',
            success: function (data) {
                $("#userMappingsBlock").empty();
                $('#userMappingsBlock').html(data.output);
            }
        });

        $("#newUsername").val(null);
        $('#newTargetRole').prop('selectedIndex',0)
    }

    function deleteMapping(mappingId) {
        $.ajax({
            url: '/app/sec/user/deleteRoleMigrationMapping',
            data: {
                mappingId: mappingId
            },
            type: 'POST',
            success: function (data) {
                $("tr[id='" + mappingId + "']").remove()
            }
        });

        $('#newTargetRole').prop('selectedIndex',0)
    }

    function updateMapping(mappingId) {
        const targetRole = $("select[id=targetRole_"+mappingId+"]").children("option:selected").val();

        $.ajax({
            url: '/app/sec/user/addRoleMigrationMapping',
            data: {
                targetRole: targetRole,
                mappingId: mappingId,
            },
            type: 'POST'
        });

        $('#newTargetRole').prop('selectedIndex',0)
    }

    function delay(callback, ms) {
        let timer = 0;
        return function() {
            let context = this, args = arguments;
            clearTimeout(timer);
            timer = setTimeout(function () {
                callback.apply(context, args);
            }, ms || 0);
        };
    }

    function uploadOnKeyUpUser() {
        delay(function () {
            $.ajax({
                url: "/app/sec/user/uploadOnKeyUpUsers",
                method: "POST",
                data: {
                    q: $("#newUsername").val()
                },
                success: function (data) {
                    const result = data.output;
                    $("#users").empty();

                    for (let i = 0; i < result.length; i++) {
                        $("<option value=" + result[i] + ">").appendTo($("#users"));
                    }

                    $("#newUsername").focus();
                }
            });
        }, 700).call();

        $('#newTargetRole').prop('selectedIndex',0);
    };
</script>
<div id="roleMigrationMappingTabContent" style="display: none;">
    <div>
        <div style="display: flex">
            <div style="padding-right: 20px">
                <label>
                    User
                    <input type="text" name="newUsername" id="newUsername" list="users" onkeyup="uploadOnKeyUpUser()" autocomplete="off">
                    <datalist id="users">
                    </datalist>
                </label>
            </div>
            <div style="padding-right: 20px">
                <label>
                    Target Role
                    <select name="newTargetRole" id="newTargetRole">
                        <option value="">--Please choose an option--</option>
                        <g:each in="${roles}" var="role">
                            <option value="${role.authority}">${role.authority}</option>
                        </g:each>
                    </select>
                </label>
            </div>
            <div>
                <button class="btn btn-primary" onclick="addMapping()">Save</button>
            </div>
        </div>

        <div id="userMappingsBlock">
            <g:render template="tabs/blocks/roleMigrationUserTable" model="[roleMigrationMappings: roleMigrationMappings, roles: roles]"/>
        </div>
    </div>
</div>

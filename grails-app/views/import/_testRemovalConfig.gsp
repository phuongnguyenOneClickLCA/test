<div class="tenMarginVertical">
    <a href="javascript:" onclick="$('#testConfigDiv').toggleClass('hidden')">Test updateStringOnImport config</a>

    <div id="testConfigDiv" class="hidden">
        <div>
            <div>Use this to test the string and updateStringOnImport config to remove something while importing the NMD (both API and excel)</div>

            <div>Enter the string and config without the " " of the JSON, example in config <b>"Deelproduct"</b> then just enter <b>Deelproduct</b>
            </div>
        </div>

        <div class="flex">
            <div>
                <b>Test string:</b>
                <label>
                    <input id="testString" type="text" placeholder="Enter test string"/>
                </label>
            </div>

            <div class="fiveMarginHorizontal">
                <b>
                    Config:
                </b>

                <div class="flex">
                    <label>
                        <input id="sourceString" type="text" placeholder="Enter source string (to be replaced)"/>
                        <input id="targetString" type="text" placeholder="Enter target string (replace with)"/>
                    </label>

                    <div>
                        <a href="javascript:" class="btn btn-primary" onclick="testConfig()">
                            Test
                        </a>
                    </div>
                </div>
            </div>

        </div>

    </div>

</div>
<script>
    function testConfig() {
        const testString = $('#testString').val()
        const sourceString = $('#sourceString').val()
        const targetString = $('#targetString').val()

        var json = {testString: testString, source: sourceString, target: targetString};

        $.ajax({
            data: JSON.stringify(json),
            contentType: "application/json; charset=utf-8",
            type: "POST",
            url: '/app/sec/nmdApi/testNmdConfigRemoval',
            success: function (data) {
                Swal.fire({
                    title: 'Response',
                    text: data.output
                })
            },
            error: function (error) {
                somethingWrongErrorSwal()
            }
        })
    }
</script>
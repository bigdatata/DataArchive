/**
 * Created by luotao on 2016/6/13.
 */
$(document).ready(function(){
    function changeTable(dataSourceId,tableId){
        var dataSource=$("#"+dataSourceId).val();
        $.ajax({
            url: "/archive/getTable",
            type: "post",
            data: { dataSource :dataSource },
            dataType: "json"
        }).done(function( msg ) {
            var oldValue= $("#"+tableId).data('table');
            var selectOption="<option>请选择</option>";
            for(var i=0;i<msg.length;i++){
                if(oldValue&&msg[i]==oldValue){
                    selectOption+='<option selected>'+msg[i]+'</option>';
                }else{
                    selectOption+='<option>'+msg[i]+'</option>';
                }

            }
            $("#"+tableId).html(selectOption);
        });
    }
    changeTable("sourceDatabase","sourceTable");
    changeTable("targetDatabase","targetTable");
    $("#sourceDatabase").change(function(){
        changeTable("sourceDatabase","sourceTable");
    });
    $("#targetDatabase").change(function(){
        changeTable("targetDatabase","targetTable");
    });
});

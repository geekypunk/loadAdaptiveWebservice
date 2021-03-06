
		$(function(){
			
			$.getJSON("ml/svm/getTrainingDataSets",{id: $(this).val(), ajax: 'true'}, function(j){
		 		var $subType = $("#e1");
				$subType.empty();
				$.each(j, function () {
					$subType.append($('<option></option>').attr("value", this.optionValue).text(this.optionDisplay));
				});
			});
			$.getJSON("ml/svm/getTestDataSets",{id: $(this).val(), ajax: 'true'}, function(j){
		 		var $subType = $("#e2");
				$subType.empty();
				$.each(j, function () {
					$subType.append($('<option></option>').attr("value", this.optionValue).text(this.optionDisplay));
				});
			});
			
		
		})
	
		$("#svmForm").submit(function(event) {

			  /* stop form from submitting normally */
			event.preventDefault();

			var a=$('#btn-load-complete');
			a.button("loading");
			var trainingDataset = $('#e1').val();
			if(trainingDataset.trim().length > 0 && trainingDataset.trim().indexOf(".train")!=-1){	
				$.ajax({
					type: "POST",
					data:{ 
						trainingDataset: trainingDataset, 
						testDataset: $('#e2').val() 
					},
					dataType: "text",
					url: "ml/svm/runDistributedService",
					success: function (d) {
						a.button("complete");
					},
					error: function (d) {
		
					},
					complete: function (d) {
						$('#btn-load-complete').button("complete");
						
						
						
					}
				});
			}else{
				alert("Choose a training dataset!");
				$('#btn-load-complete').button("reset");
			}
		
		});
			
		$("#btn-chart").on("click",function(){
			$('#svmChart').show();
			var a=$(this);
			a.button("loading");
			setTimeout(function() { 
				$.ajax({
					type: "GET",
					dataType: "text",
					url: "ml/svm/getReport/SVM.train-SVM.test.chart",
					success: function (d) {
						a.button("complete");
						SVMCharts.displayChart(d);
					}
				})
				},500)
		});
		

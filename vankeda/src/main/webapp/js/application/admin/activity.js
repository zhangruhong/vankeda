$(function(){
	
	var $actForm = $('#activityForm');
	var $filUpload = $('#file-upload');
	var $actModal = $('#activityModal');
	
	$actForm.on('submit', function(e){
		e.preventDefault();
		$.ajax({
			url: '/admin/activity',
			type: $('[name=id]', this).val() ? 'PUT' : 'POST',
			data: this
		}).done(function(){
			window.location.reload();
		});
	});
	
	$('[name=startDate],[name=endDate]', $actForm).datetimepicker({
	    format: 'yyyy-mm-dd hh:ii'
	});
	
	showActivityModal = function(activityId){
		if(!activityId){
			$actForm.resetForm();
			$filUpload.fileUpload({}, 'empty');
			$filUpload.fileUpload({inputName : 'pictPath'});
			$actModal.modal('show');
		}else{
			$.ajax({url: '/admin/activity/' + activityId}).done(function(activity){
				$actForm.fillForm(activity);
				$filUpload.fileUpload({inputName: 'pictPath', files : activity.pictPath}, 'fill');
				$actModal.modal('show');
			});
		}
	}
	
	submit = function(){
		$actForm.trigger('submit');
	}
	
});
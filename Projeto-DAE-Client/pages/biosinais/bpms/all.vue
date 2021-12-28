<template>
  <div>
    <b-container class="modal-content rounded-6 shadow" >
      <caption style="text-align:center">Colestrol</caption>
      <b-table striped  :items="colestrol" :fields="fields" title="Colestrol" style="float:left;">
        <template v-slot:cell(actions)="row">
          <nuxt-link
            class="btn btn-link"
            :to="`/students/${row.item.username}/details`">Details</nuxt-link>
          <nuxt-link
            class="btn btn-link"
            :to="`/students/${row.item.username}/updateStudent`">Update</nuxt-link>
          <nuxt-link :to="`/students/${row.item.username}/send-email`">Send email</nuxt-link>
        </template>
      </b-table>
      <nuxt-link to="/">Back</nuxt-link>
    </b-container>
    <nuxt-link to="/students/createStudent">Create a New Student</nuxt-link>
  </div>
</template>

<script>
export default {
  name: "all"
}
</script>

<style scoped>

</style>

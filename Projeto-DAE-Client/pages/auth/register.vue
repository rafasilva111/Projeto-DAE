<template>
  <b-container>
    <h3 style="text-align: center">Academics Management</h3>
    <h3 style="text-align: center" class="pb-4">Register Form</h3>
    <b-container class="modal-content rounded-6 shadow p-4">
    <b-form @submit.prevent="onSubmit" @reset="onReset">
      <b-form-group label="Username" description="Enter your desired username">
        <b-input
          name="username"
          placeholder="Insert Username"
          v-model.trim="username"
          required />
      </b-form-group>
      <b-form-group label="Password" description="Enter your desired password">
        <b-input
          name="password"
          type="password"
          placeholder="Insert Password"
          v-model="password"
          required />
      </b-form-group>
      <b-form-group label="Email" description="Enter your desired email">
        <b-input
          name="email"
          type="email"
          placeholder="Insert Email"
          v-model="email"
          required />
      </b-form-group>
      <h6>Medicos Disponiveis</h6>
      <select v-model="selected" style="text-align:center">
        <option v-for="item in medicos" :value="item" :key="item.id">
          {{"Doctor: "+ item.username +" /  Email: "+ item.email }}
        </option>
      </select>
      <p>Enter your desired doctor</p>
      <b-button type="reset" class="btn-warning">Reset</b-button>
      <b-button type="submit" class="btn-success">Submit</b-button>
    </b-form>
      </b-container>
  </b-container>
</template>
<script>
export default {
  auth: false,
  data() {
    return {
      username: null,
      password: null,
      email:null,
      medicos:[],
      selected:null,
    }
  },
  created () {
        this.$axios.$get('/api/user/alldocs/')
          .then((medicos) => {
            this.medicos = medicos
            console.log(medicos);
          })
  },
  methods: {
    onSubmit() {
      let promise = this.$auth.loginWith('local', {
        data: {
          username: this.username,
      password: this.password
    }
    })
      promise.then(() => {
        this.$toast.success('You are logged in!').goAway(3000)
        // check if the user $auth.user object is set
        console.log(this.$auth.user)
        // TODO redirect based on the user role
        // eg:
        if (this.$auth.user.groups.includes('UtilizadorNormal') || this.$auth.user.groups.includes('Administrador')) {
          this.$router.push('/')
        }
      })
      promise.catch(() => {
        this.$toast.error('Sorry, you cant register. Ensure your credentials are compliant').goAway(3000)
      })
    },
    onReset() {
      this.username = null
      this.password = null
    }
  }
}
</script>

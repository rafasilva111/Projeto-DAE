<template>
  <div class="">
    <div>
      <div style="width: 100%; display: table;">
        <div v-for="outro in outrosCat" style="display: table-row">
          <div class="p-3" style="width: 600px; display: table-cell; ">
            <b-container class="modal-content rounded-4 shadow p-4" fluid="sm">
              <h1 class="pb-4" style="text-align:center">{{outro.name}}</h1>
              <b-table striped :items="pesagem" :fields="fields" style="float:left;">
                <template v-slot:cell(actions)="row">
                  <nuxt-link
                    class="btn btn-dark btn-sm"
                    :to="`/biosinais/pesagem/${row.item.id}/updatePesagem`">Atualizar
                  </nuxt-link>
                  <button
                    class="btn btn-danger btn-sm"
                    @click="apagar(row.item.id)">Apagar
                  </button>
                </template>
                <template v-slot:cell(IMC)="row">
                  {{ IMCcomputed(row.item.height, row.item.weight) }}
                </template>
              </b-table>
              <b-row>
                <b-col lg="6" class="pb-4">
                  <nuxt-link class="btn btn-dark btn-sm" to="/biosinais/pesagem/createPesagem">Inserir Registo
                  </nuxt-link>
                  <button  class="btn btn-danger btn-sm float-right" to="/biosinais/pesagem/createPesagem">Eliminar Tipo
                  </button>
                </b-col>
              </b-row>
            </b-container>
          </div>


        </div>
      </div>
      <div>
        <b-container>
          <div>
            <b-button class="btn btn-dark btn-sm" float @click.prevent="adicionarTipo">Adicionar novo tipo de dado Biomedico</b-button>
          </div>
        </b-container>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      graphData: [],
      ready: false,
      labels: [],
      data: [],

      users: [1,2,3],
      fields: [
        {
          key: 'name',
          label: 'Utente',
        },
        {
          key: 'date',
          label: 'Data',
        },
        {
          key: 'value[0]',
          label: 'Altura',
        },
        {
          key: 'value[1]',
          label: 'Peso',
        },
        {
          key: 'value[2]',
          label: 'IMC',
        },
        {
          key: 'classification',
          label: 'Classificação',
        },
        {
          key: 'descricao',
          label: 'Descrição',
        },
        {
          key: 'actions',
          label: 'Actions',

        }
      ],
      outrosCat: [],
      user: null,


    }
  },
  created() {

        this.$axios.$get('/api/admin/outro')
          .then((outrosCat) => {
            this.outrosCat = outrosCat
          })
  },

  methods: {
    apagar: function (value) {

      this.$axios.$delete('/api/biosinais/pesagem/' + value)
        .then(() => {
          this.$axios.$get('/api/biosinais/pesagem/' + this.user.id)
            .then((pesagem) => {
              this.pesagem = pesagem
            })


        })
    },
    IMCcomputed: function (altura, peso) {
      // `this` points to the vm instance
      console.log(altura + " " + peso);
      return peso * (altura * altura);
    },

    adicionarTipo(){

    }

  },
  computed: {
    // a computed getter

  }
}
</script>
